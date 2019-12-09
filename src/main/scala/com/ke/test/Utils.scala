package com.ke.test

import java.io.File
import java.text.SimpleDateFormat
import org.apache.spark.sql.DataFrame

/**
 * An object to facilitate utility functions to be used by other classes and objects.
 */
object Utils {
  /**
   * A function to read the contents of a Spark dataframe and create a DDL, which can be used to create a Hive table.
   * @param dataframe
   * @param tableName
   * @param absFilePath
   * @param partitions
   * @return String
   * @example
   *          {{{val new_ddl = dataFrameToDDL(target_dataframe)}}}
   */
  def dataFrameToDDL(dataframe: DataFrame, tableName: String, absFilePath: StringContext, partitions: String = null): String ={
    val columns = dataframe.schema.map { field =>
      field.name + " " + field.dataType.simpleString.toUpperCase
    }
    if(partitions.isEmpty) {
      s"CREATE EXTERNAL TABLE $tableName (\n${columns.mkString(",\n")}\n) \n STORED AS PARQUET \n LOCATION '"+absFilePath+"'"
    }
    else{
      s"CREATE EXTERNAL TABLE $tableName (\n${columns.mkString(",\n")}\n) \n PARTITIONED BY ($partitions) \n STORED AS PARQUET \n LOCATION '"+absFilePath+"'"
    }
  }
  /**
   * A function to convert a string of common date format '''yyyy-MM-dd''' to string format of '''yyyyMMDD'''.
   *
   * @param arg
   * @return String0
   *         * @example
   *         *          {{{val DateToPartition(string_yyyy-MM-dd)}}}
   */
  def DateToPartition(arg: String): Unit = {
    val in = new SimpleDateFormat("yyyy-MM-dd")
    val out = new SimpleDateFormat("yyyyMMdd")
    val x = arg
    val formattedDate = out.format(in.parse(x))
  }
  /**
   * A function to to find .SQL and .HQl files in a specified directory and store then in a Scala list.
   *
   * @param String
   * @return String0
   *         * @example
   *         *          {{{val ListFiles('/datalake/some/path')}}}
   */
  def ListFiles(dir: String): List[String] = {
    val file = new File(dir)
    file.listFiles.filter(_.isFile)
      .filter { f => f.getName.endsWith(".sql") || f.getName.endsWith(".hql")}
      .map(_.getPath).toList.filter(_.nonEmpty)
  }

}
