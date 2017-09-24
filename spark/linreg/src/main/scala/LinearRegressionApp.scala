import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.sql.Dataset
import org.apache.spark.mllib.random.RandomRDDs._
import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.apache.spark.sql._
import org.apache.spark.sql.types._
import org.apache.spark.ml.linalg.Vectors


object LinearRegressionApp extends App {

  // Initializing context
  import org.apache.spark.sql.SparkSession
  import org.apache.spark.sql.functions._
  val sparkSession: SparkSession =
    SparkSession
      .builder()
      .appName("LinearRegression")
      .config("spark.master", "local")
      .getOrCreate()
  import sparkSession.implicits._
  val sc = sparkSession.sparkContext

  // Base function and noise
  val noise = 0.3; val size = 100
  def func(x: Double) = x * 2.7 + 2.0

  // Sampling
  val seed = 2804
  val X = uniformRDD(sc, size, seed = seed).map(v => 2.0 * v)
  val epsilons = normalRDD(sc, size, seed = seed).map(v => noise * v)
  val y = X.zip(epsilons).map { case (x, epsilon) => func(x) + epsilon }

  // TODO: Drawing scatter and original line

  // Fitting regression
  val trainingRDD = X.zip(y).map {
    case (x, v) => (Vectors.dense(x), v)
  }
  val training = trainingRDD.toDF("features", "label")
  val lr = new LinearRegression()
  val model = lr.fit(training)
  val trainingSummary = model.summary
  println(s"${model.intercept}, ${model.coefficients}")

  // TODO: Drawing all togetger

}
