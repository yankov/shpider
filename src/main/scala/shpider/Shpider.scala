package shpider

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter
import backtype.storm.Config
import backtype.storm.LocalCluster
import backtype.storm.StormSubmitter
import backtype.storm.task.OutputCollector
import backtype.storm.testing.TestWordSpout
import backtype.storm.topology.TopologyBuilder
import backtype.storm.tuple.Fields
import backtype.storm.utils.Utils
import storm.kafka._
import shpider.bolt.CounterBolt
import java.util.ArrayList
import backtype.storm.topology.BasicOutputCollector
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseBasicBolt
import backtype.storm.tuple.Tuple
import backtype.storm.spout.SchemeAsMultiScheme

object Shpider extends App {
  println("Starting shpider ...")

  val system = ActorSystem("MySystem")

  Router.fetcher ! "http://techcrunch.com"

}

object Router {
  val system = ActorSystem("MySystem")

  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 10)), "fetcherRouter")
  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "filterRouter")
  val reporter = system.actorOf(Props[Reporter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "reporterRouter")
}
