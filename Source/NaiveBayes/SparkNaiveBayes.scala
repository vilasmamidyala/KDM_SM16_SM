/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package mlpipeline

import ontInterface.OwlPizza
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.feature._
import org.apache.spark.mllib.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.immutable.HashMap

object SparkNaiveBayes {


  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "C:\\winutils")

    //val trainFolder = "data/Categories/*"
    val trainFolder="C:\\SUMMER\\Data1\\*"
    //val conf = new SparkConf().setAppName(s"NBExample").setMaster("local[2]").set("spark.driver.memory", "2g").set("spark.executor.memory", "2g")
    val conf = new SparkConf().setAppName(s"NBExample").setMaster("local[2]")
     /* .set("spark.akka.frameSize","1200")
      .set("spark.io.compression.codec","lzf")
      .set("spark.driver.maxResultSize","1g")
      .set("spark.default.parallelism","2")
      .set("spark.executor.cores","2")
      .set("spark.executor.instances","1")
      .set("spark.serializer","org.apache.spark.serializer.KryoSerializer")*/


    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)


    // Load documents, and prepare them for NB.
    val preprocessStart = System.nanoTime()
    val (input, corpus) =
      preprocess(sc, trainFolder)

    input.foreach(f=>println(f))
    corpus.foreach(f=>println(f))

    var hm = new HashMap[String, Int]()
    //val CATEGORIES = List("Taste", "Toppings")
    val CATEGORIES = List("positive","negative")
    var index = 0
    CATEGORIES.foreach(f => {
      hm += CATEGORIES(index) -> index
      index += 1
    })
    val mapping = sc.broadcast(hm)
    val data = input.zip(corpus)
    val featureVector = data.map(f => {
      val location_array = f._1._1.split("/")
      val class_name = location_array(location_array.length - 2)

      new LabeledPoint(hm.get(class_name).get.toDouble, f._2)
    })

    println("Feature vector input to naive bayes:")
    featureVector.foreach(f=>println(f))

    val model: NaiveBayesModel = NaiveBayes.train(featureVector, lambda = 1.0, modelType = "multinomial")
    model.save(sc,"data/naivebayesmodel")

    val testDir = sc.textFile("data/test/103.txt")
    val x=testDir.toString.split("\n")

    //val topicData = SparkLDAMain.main(sc, testDir, 3, "em")

    val testFV = getTFIDFVector(sc, x)

    println("testFV:")
    testFV.foreach(f=>println(f))

    val result = model.predict(testFV)

    result.foreach(f => println(f))

    sc.stop()
  }

  private def preprocess(sc: SparkContext,
                         paths: String): (RDD[(String, String)], RDD[Vector]) = {

    val sqlContext = SQLContext.getOrCreate(sc)
    import sqlContext.implicits._
    val df = sc.wholeTextFiles(paths).coalesce(1).map(f => {
      var ff = f._2.replaceAll("[^a-zA-Z\\s:]", " ")
      ff = ff.replaceAll(":", "")
      // println(ff)
      (f._1, CoreNLP.returnLemma(ff))
    }).toDF("location", "docs")


    val tokenizer = new RegexTokenizer()
      .setInputCol("docs")
      .setOutputCol("rawTokens")
    val stopWordsRemover = new StopWordsRemover()
      .setInputCol("rawTokens")
      .setOutputCol("tokens")

    val tf = new org.apache.spark.ml.feature.HashingTF()
      .setInputCol("tokens")
      .setOutputCol("features")
    val idf = new org.apache.spark.ml.feature.IDF()
      .setInputCol("features")
      .setOutputCol("idfFeatures")

    val w2v=new Word2Vec()
      .setInputCol("tokens")
      .setOutputCol("wordvectors").setMinCount(0)

    val scaler = new MinMaxScaler()
      .setInputCol("wordvectors")
      .setOutputCol("scaledFeatures")


    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, stopWordsRemover, tf, idf,w2v,scaler))

    val model = pipeline.fit(df)
    val documents = model.transform(df)
      .select("scaledFeatures")
      .rdd
      .map { case Row(scaledFeatures: Vector) => scaledFeatures }

    // sc.broadcast(model)

    model.save("data/pipelinemodel")

    val input = model.transform(df).select("location", "docs").rdd.map { case Row(location: String, docs: String) => (location, docs) }
    println(model.transform(df).printSchema())
    println("Preprocessing output")
    input.foreach(f=>println(f))
    println("documents variable:")
    documents.foreach(f=>println(f))
    (input, documents)
  }

  def getTFIDFVector(sc: SparkContext, input: Array[String]): RDD[Vector] = {

    val sqlContext = SQLContext.getOrCreate(sc)
    import sqlContext.implicits._
    val df = sc.parallelize(input.toSeq).toDF("docs")


    val tokenizer = new RegexTokenizer()
      .setInputCol("docs")
      .setOutputCol("rawTokens")
    val stopWordsRemover = new StopWordsRemover()
      .setInputCol("rawTokens")
      .setOutputCol("tokens")

    val tf = new org.apache.spark.ml.feature.HashingTF()
      .setInputCol("tokens")
      .setOutputCol("features")
    val idf = new org.apache.spark.ml.feature.IDF()
      .setInputCol("features")
      .setOutputCol("idfFeatures")

    val w2v=new Word2Vec()
      .setInputCol("tokens")
      .setOutputCol("wordvectors").setMinCount(0)

    val scaler = new MinMaxScaler()
      .setInputCol("wordvectors")
      .setOutputCol("scaledFeatures")


    val pipeline = new Pipeline()
      .setStages(Array(tokenizer, stopWordsRemover, tf, idf,w2v,scaler))

    val model = pipeline.fit(df)
    val documents = model.transform(df)
      .select("scaledFeatures")
      .rdd
      .map { case Row(scaledFeatures: Vector) => scaledFeatures }

    documents
  }
}


