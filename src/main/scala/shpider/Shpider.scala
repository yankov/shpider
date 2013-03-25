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

import shpider.bolt._

object Shpider extends App {
  val system = ActorSystem("MySystem")

  val builder = new TopologyBuilder()

  builder.setSpout("word", new TestWordSpout(), 10);
  builder.setBolt("count1", new ExclamationBolt(), 3)
    .shuffleGrouping("word");

  val conf = new Config()
  conf.setDebug(true);

  val cluster = new LocalCluster()
  cluster.submitTopology("test", conf, builder.createTopology());
  Utils.sleep(10000);
  cluster.killTopology("test");
  cluster.shutdown();

  Router.fetcher ! "http://techcrunch.com"
}

object Router {
  val system = ActorSystem("MySystem")

  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 10)), "fetcherRouter")
  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "filterRouter")
  val reporter = system.actorOf(Props[Reporter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "reporterRouter")
}
