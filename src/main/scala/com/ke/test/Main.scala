package com.ke.test

import org.apache.spark.sql.SparkSession
import org.slf4j.LoggerFactory
/**
 * An object to facilitate base application functions including logging ans Spark Session.
 */
object Main {
  /**
   * A function to call the logger between classes and objects.
   * @return logger
   * @example
   *          {{{val logger: Logger = Main.getLogger()}}}
   */
  def getLogger = LoggerFactory.getLogger(getClass)
  /**
   * A function to call Spark config.
   * @return SparkSession
   * @example
   *          {{{val sc = Main.getSession}}}
   */
  def getSession = SparkSession.builder().
    appName("test").
    config("spark.serializer", "org.apache.spark.serializer.KryoSerializer").
    config("hive.exec.dynamic.partition", "true").
    config("hive.exec.dynamic.partition.mode", "nonstrict").
    config("parquet.compression", "SNAPPY").enableHiveSupport().getOrCreate()

}
