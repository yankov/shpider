import sbt._
import sbt.Keys._

object G8testBuild extends Build {

  lazy val shpider = Project(
    id = "shpider",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "shpider",
      organization := "org.example",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.9.3",
      libraryDependencies += "redis.clients" % "jedis" % "2.1.0",
      libraryDependencies += "io.netty" % "netty" % "3.6.3.Final",
      libraryDependencies += "net.databinder.dispatch" % "dispatch-core_2.9.2" % "0.9.5",
      libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2",
      libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.5",
      libraryDependencies += "com.typesafe.akka" % "akka-slf4j" % "2.0.3",
      libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.9"
    )
  )
  //  resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

}