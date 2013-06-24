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
      scalaVersion := "2.10.2",
      resolvers ++= Seq(
        "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
        "twitter repo" at "http://maven.twttr.com",
        "apache" at "https://repository.apache.org/content/repositories/snapshots/"
      ),

      libraryDependencies ++= Seq(
        "net.databinder.dispatch" %% "dispatch-core" % "0.10.0",
        "org.jsoup" % "jsoup" % "1.7.2",
        "com.typesafe.akka" % "akka-actor_2.10" % "2.1.4",
        "com.typesafe.akka" %% "akka-slf4j" % "2.1.2",
        "log4j" % "log4j" % "1.2.16",
        "com.101tec" % "zkclient" % "0.3"
      )
    )
  )
}

