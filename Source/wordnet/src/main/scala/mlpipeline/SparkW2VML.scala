package mlpipeline

import java.io.PrintStream

import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.feature._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 21-Jun-16.
  */
object SparkW2VML {
  def main(args: Array[String]) {

    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val spark = SparkSession
      .builder
      .appName("SparkW2VML")
      .master("local[*]")
      .getOrCreate()


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF);
    Logger.getLogger("akka").setLevel(Level.OFF);

    // Read the file into RDD[String]
    val input = sc.textFile("data/TwitterTweets75.txt", 500).map(line => {
      //Getting Lemmatized Form of the word using CoreNLP
      val lemma = CoreNLP.returnLemma(line)
      (0, lemma)
    })


    //Creating DataFrame from RDD

    val sentenceData = spark.createDataFrame(input).toDF("labels", "sentence")

    //Tokenizer
    val tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words")
    val wordsData = tokenizer.transform(sentenceData)

    //Stop Word Remover
    val remover = new StopWordsRemover()
      .setInputCol("words")
      .setOutputCol("filteredWords")
    val processedWordData = remover.transform(wordsData)

    //NGram
    val ngram = new NGram().setInputCol("filteredWords").setOutputCol("ngrams")
    val ngramDataFrame = ngram.transform(processedWordData)
    ngramDataFrame.take(3).foreach(println)
    println(ngramDataFrame.printSchema())
// For Output in page by vikesh
val topic_output = new PrintStream("C:\\SUMMER\\KDM\\CS5560-T7SourceCode\\CS5560 - Tutorial 7 Source Code\\SparkOpenIE\\word2vector.txt")
//    topic_output.println(ngramDataFrame.printSchema())

    //TFIDF TopWords
    val topWords = TFIDF.getTopTFIDFWords(sc, ngramDataFrame.select("ngrams").rdd)
    println("TOP WORDS: \n\n" + topWords.mkString("\n"))

    val newDataFrame = ngramDataFrame.na.drop()

    println(newDataFrame.printSchema())

    // For Output in page by vikesh
   // val topic_output1 = new PrintStream("C:\\SUMMER\\KDM\\CS5560-T7SourceCode\\CS5560 - Tutorial 7 Source Code\\SparkOpenIE\\word2vector.txt")
   // topic_output.println(newDataFrame.printSchema())


    //Word2Vec Model Generation


    val word2Vec = new Word2Vec()
      .setInputCol("ngrams")
      .setOutputCol("result")
      .setVectorSize(3)
      .setMinCount(0)

    val struct = StructType(StructField("labels", IntegerType, false) ::
      StructField("sentence", StringType, true) ::
      StructField("words", ArrayType(StringType,true), true)::
        StructField("filteredWords",ArrayType(StringType,true), true)::
        StructField("ngrams", ArrayType(StringType,true), true) :: Nil
      )
    val ss=spark.createDataFrame(sc.parallelize(ngramDataFrame.collect()),struct)

    val model = word2Vec.fit(ss)


    //Finding synonyms for TOP TFIDF Words using Word2Vec Model
    topWords.foreach(f => {
      println(f._1 + "  : ")
     // topic_output.println(f._1 + "  : ") // added by vikesh
      val result = model.findSynonyms(f._1, 3)
      result.take(3).foreach(println)
      println
    })
   // topic_output.flush()
    //topic_output.close()
    spark.stop()
  }

}
