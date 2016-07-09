package ner

import mlpipeline.CoreNLP
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 29-Jun-16.
  */
object SparkNER {
  def main(args: Array[String]) {
    System.setProperty("hadoop.home.dir", "E:\\winutils")
    val conf = new SparkConf().setAppName(s"NERTrain").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)


    val Domain_Based_Words = sc.broadcast(sc.textFile("E:\\study\\Study\\KDM\\CS5560-T8-SourceCode\\CS5560 - Tutorial 8 Source Code\\SparkLDA\\data\\ner\\domainBasedWords").map(CoreNLP.returnLemma(_)).collect())

    val input = sc.textFile("E:\\study\\Study\\KDM\\CS5560-T8-SourceCode\\CS5560 - Tutorial 8 Source Code\\SparkLDA\\data\\ner\\sample2").flatMap(f => {
      val s=f.replace("[^a-zA-Z\\s]","")
      s.split(" ")}).map(ff => {
      val f = CoreNLP.returnLemma(ff)
      if (Domain_Based_Words.value.contains(f))
        (f, "POLL")
      else
        (f, CoreNLP.returnNER(f))
    }

    )

    println(input.collect().mkString("\n"))
  }

}
