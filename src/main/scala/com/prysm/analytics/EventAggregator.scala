package com.prysm.analytics

import kafka.serializer.StringDecoder

import org.apache.spark.streaming._
import org.apache.spark.streaming.kafka._
import org.apache.spark.SparkConf
import org.apache.spark.sql.{ Row }
import com.datastax.spark.connector._

import org.json4s._
import org.json4s.native.JsonParser
import java.util.UUID
import java.sql.Date

object EventAggregator {
  
  import com.datastax.spark.connector.streaming._
  
  def main(args: Array[String]) {
    if (args.length < 2) {
      System.err.println(s"""
        |Usage: EventAggregator <brokers> <topics>
        |  <brokers> is a list of one or more Kafka brokers
        |  <topics> is a list of one or more kafka topics to consume from
        |
        """.stripMargin)
      System.exit(1)
    }

    import Events._

    val Array(brokers, topics) = args

    val sparkConf = new SparkConf().setAppName("EventAggregator")
      .set("spark.cassandra.connection.host", "temp-elk-nonprod-0.northcentralus.cloudapp.azure.com")
      .set("spark.cassandra.auth.username", "cassandra")
      .set("spark.cassandra.auth.password", "cassandra")

    val ssc = new StreamingContext(sparkConf, Seconds(60))
    
    val topicsSet = topics.split(",").toSet
    val kafkaParams = Map[String, String]("metadata.broker.list" -> brokers)
    val events = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](
      ssc,
      kafkaParams,
      topicsSet)

    val raw_events = events.map {
      case (_, v) =>
        { implicit val formats = DefaultFormats; JsonParser.parse(v).extract[RawEvent] }
    }
    raw_events.saveToCassandra("monitoring", "raw_events")
    ssc.start()
    ssc.awaitTermination()
  }
}