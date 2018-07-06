name := "Kafka_SparkStreaming"

version := "0.1"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.1.2" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.1.2" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "2.1.2"
//libraryDependencies += "org.elasticsearch" %% "elasticsearch-spark" % "2.1.2"
libraryDependencies += "org.scalaj" % "scalaj-http_2.11" % "2.3.0"


assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @ _*) => MergeStrategy.discard
  case x => MergeStrategy.first
}