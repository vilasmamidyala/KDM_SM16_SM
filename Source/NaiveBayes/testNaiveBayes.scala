package mlpipeline

/**
  * Created by Kiran on 7/25/2016.
  */
import org.apache.spark.ml.PipelineModel
import org.apache.spark.mllib.classification.NaiveBayesModel
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{Row, SQLContext}
import org.apache.spark.{SparkContext, SparkConf}

/**
  * Created by Manikanta on 7/20/2016.
  */
object testNaiveBayes {


  def main(args: Array[String]) {

    System.setProperty("hadoop.home.dir", "C:\\winutils");

    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkTFIDF").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)

    val sqlContext = SQLContext.getOrCreate(sc)
    import sqlContext.implicits._

    val paths="data/test/*"
    val df = sc.wholeTextFiles(paths).map(f => {
      var ff = f._2.replaceAll("[^a-zA-Z\\s:]", " ")
      ff = ff.replaceAll(":", "")
      // println(ff)
      (f._1, CoreNLP.returnLemma(ff))
    }).toDF("location", "docs")



    val model=PipelineModel.load("data/pipelinemodel")

    val pipelinedf=model.transform(df)

    val test: RDD[Vector] =pipelinedf.select("scaledFeatures")
      .rdd
      .map { case Row(scaledFeatures: Vector) => scaledFeatures }

    val classifmodel=NaiveBayesModel.load(sc,"data/naivebayesmodel")

    val result=classifmodel.predict(test)
    result.foreach(f=>println(f))




  }
}