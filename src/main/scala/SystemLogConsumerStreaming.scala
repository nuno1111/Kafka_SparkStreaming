
import org.apache.spark._
import org.apache.spark.streaming._
//import org.apache.spark.streaming.StreamingContext._ // not necessary since Spark 1.3

object SystemLogConsumerStreaming {
  def main(args: Array[String]): Unit = {

    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent a starvation scenario.

    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(conf, Seconds(1))

  }
}



