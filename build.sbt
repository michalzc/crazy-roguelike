
name := "Crazy Roguelike"
version := "0.0.1-SNAPSHOT"
scalaVersion := "2.12.1"

libraryDependencies ++= Seq(
  //akka
  "com.typesafe.akka" %% "akka-actor" % "2.4.17",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.17",

  //logging
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.slf4j" % "slf4j-api" % "1.7.24",
  "org.apache.logging.log4j" % "log4j-slf4j-impl" % "2.8",
  "org.apache.logging.log4j" % "log4j-api" % "2.8",

  //testing
  "org.scalatest" %% "scalatest" % "3.0.1" % "test"

)

scalacOptions += "-feature"
fork in Test := true
fork in run := true