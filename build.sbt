resolvers += "Java.net Maven2 Repository" at "http://download.java.net/maven/2/"

resolvers += "apache" at "https://repository.apache.org/content/repositories/snapshots/"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Clojars" at "http://clojars.org/repo"

name := "shpider"

version := "1.0"

scalaVersion := "2.9.3"

libraryDependencies += "redis.clients" % "jedis" % "2.1.0"

libraryDependencies += "io.netty" % "netty" % "3.6.3.Final"

libraryDependencies += "net.databinder.dispatch" % "dispatch-core_2.9.2" % "0.9.5"

libraryDependencies += "org.jsoup" % "jsoup" % "1.7.2"

libraryDependencies += "com.typesafe.akka" % "akka-actor" % "2.0.5"

libraryDependencies += "com.typesafe.akka" % "akka-slf4j" % "2.0.3" 
 
libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.0.9"

libraryDependencies += "storm" % "storm" % "0.8.2" % "provided"



