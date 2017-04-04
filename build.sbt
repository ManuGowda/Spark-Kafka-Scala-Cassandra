name := "Prysm-Analytics"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.0.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming" % "2.0.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "2.0.0" % "provided"
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-8" % "2.0.0"
libraryDependencies += "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0"
libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.0"
libraryDependencies += "joda-time" % "joda-time" % "2.9.7"

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}