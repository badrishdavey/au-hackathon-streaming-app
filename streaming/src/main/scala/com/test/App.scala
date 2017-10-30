/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.test

import java.io.IOException
import java.sql.Timestamp
import java.util.Properties

import com.test.beans.RecordBean
import com.test.config.ConfigurationFactory
import com.test.utils.JsonUtils
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.log4j.Logger
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types._
import org.apache.spark.sql.{Row, SQLContext, SaveMode, SparkSession}
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferBrokers
import org.apache.spark.streaming.{Seconds, StreamingContext}

object App {
  private[this] lazy val logger = Logger.getLogger(getClass)

  def jsonDecode(text: String): RecordBean = {
    try {
      JsonUtils.deserialize(text, classOf[RecordBean])
    } catch {
      case e: IOException =>
        logger.error(e.getMessage, e)
        null
    }
  }

  def main(args: Array[String]): Unit = {
    val hosts = args(0)
    val topic = args(1)
    val interval = args(2).toInt

    val spark = SparkSession.builder
      .appName("au-hackathon-streaming-app")
      //.master("local[*]")
      .getOrCreate

    val params = Map[String, Object](
      "bootstrap.servers" -> hosts,
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "auto.offset.reset" -> "latest",
      "group.id" -> "dashboard",
      "enable.auto.commit" -> (false: java.lang.Boolean)
    )

    val context = new StreamingContext(spark.sparkContext, Seconds(interval))

    val stream = KafkaUtils.createDirectStream[String, String](
      context,
      PreferBrokers,
      Subscribe[String, String](Array(topic), params)
    )

    stream.foreachRDD((rdd: RDD[ConsumerRecord[String, String]]) => {
      spark.sqlContext.createDataFrame(
        rdd.map(_.value())
          .map(jsonDecode)
          .map(row => Row.fromSeq(Seq(
            row.account_id,
            row.amount,
            row.card_number,
            row.card_type,
            row.customer_id,
            row.customer_zipcode,
            row.email,
            row.first_name,
            row.gender,
            row.is_married,
            row.last_name,
            row.merchant,
            row.rewards_earned,
            row.transaction_id,
            row.transaction_zipcode,
            row.tx_time
        ))), StructType(
          StructField("account_id", StringType) ::
          StructField("amount", StringType) ::
          StructField("card_number", StringType) ::
          StructField("card_type", StringType) ::
          StructField("customer_id", StringType) ::
          StructField("customer_zipcode", StringType) ::
          StructField("email", StringType) ::
          StructField("first_name", StringType) ::
          StructField("gender", StringType) ::
          StructField("is_married", StringType) ::
          StructField("last_name", StringType) ::
          StructField("merchant", StringType) ::
          StructField("rewards_earned", StringType) ::
          StructField("transaction_id", StringType) ::
          StructField("transaction_zipcode", StringType) ::
          StructField("tx_time", StringType) ::
          Nil
      ))
        .show()
    })

    // create streaming context and submit streaming jobs
    context.start()

    // wait to killing signals etc.
    context.awaitTermination()
  }
}