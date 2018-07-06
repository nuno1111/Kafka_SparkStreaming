
import org.apache.spark._
import org.apache.spark.streaming._
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010._
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe
import scalaj.http._
import java.util.Date

object SystemLogConsumerStreaming {
  def main(args: Array[String]): Unit = {

    val conf = new SparkConf().setMaster("local[2]").setAppName("SystemLogConsumerStreaming")

    val ssc = new StreamingContext(conf, Seconds(1))

    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "localhost:9092",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer],
      "group.id" -> "SparkStreaming-SytemLog-ConsumerGroup",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (true: java.lang.Boolean)
    )

    val topics = Array("peter-log")
    val stream = KafkaUtils.createDirectStream[String, String](
      ssc,
      PreferConsistent,
      Subscribe[String, String](topics, kafkaParams)
    )



    val format = new java.text.SimpleDateFormat("yyyy-MM-dd_HH:mm:ss.SSS")
    val today = new java.text.SimpleDateFormat("yyyy-MM-dd")
    stream.foreachRDD { rdd =>
      if( !rdd.isEmpty ) {

        val date = new Date()

        rdd.map(_.value).foreach { param =>
          val res = Http("http://localhost:9200/peter-log-" + today.format(date) + "/systemlog").postData(param)
            .header("Content-Type", "application/json")
            .header("Charset", "UTF-8")
            .option(HttpOptions.readTimeout(10000)).asString


//          println("==============================================================================================")
//          println(res)
//          println("==============================================================================================")
        }

        rdd.map(_.value).saveAsTextFile("/home/devuser/data/systemlog-"+format.format(date))



      }
    }

    println("==============================================================================================")

    ssc.start()             // Start the computation
    ssc.awaitTermination()  // Wait for the computation to terminate

  }
}



