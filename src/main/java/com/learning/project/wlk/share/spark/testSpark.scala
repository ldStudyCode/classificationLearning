package com.learning.project.wlk.share.spark

import org.apache.spark.{SparkConf, SparkContext}

object testSpark {
  def main(args: Array[String]): Unit = {
    val config = new SparkConf()
      .setAppName("test")
      .setMaster("spark://localhost:7077")
    val sc = new SparkContext(config)
    val lines = sc.textFile("D:\\IdeaProjects\\testSparkCore\\sp500.csv")
    val words  = lines.flatMap(l=>l.split(","))

    print("count:....")
    print(words.count())

//    words.saveAsTextFile("D:\\IdeaProjects\\testSparkCore\\out\\out.txt")
    words.foreach(println(_))
    sc.stop()
    print("end...")




  }
}
