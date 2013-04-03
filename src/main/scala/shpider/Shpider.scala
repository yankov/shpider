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
  val system = ActorSystem("MySystem")

  //  Router.fetcher ! "http://techcrunch.com"

  val kafkaBrokers = new ArrayList[HostPort]()
  kafkaBrokers.add(new HostPort("localhost"))

  val spoutConfig = new SpoutConfig(
    new KafkaConfig.StaticHosts(kafkaBrokers, 1), // list of Kafka brokers
    "urls", // topic to read from
    "/kafkastorm", // the root path in Zookeeper for the spout to store the consumer offsets
    "urlconsumer") // an id for this consumer for storing the consumer offsets in Zookeeper

  val builder = new TopologyBuilder()
  builder.setSpout("urls", new KafkaSpout(spoutConfig), 1)
  builder.setBolt("counter", new CounterBolt(), 1).shuffleGrouping("urls")

  val conf = new Config()
  conf.setDebug(true)

  val cluster = new LocalCluster()
  cluster.submitTopology("test", conf, builder.createTopology())

  Utils.sleep(100000)
  cluster.killTopology("test")
  cluster.shutdown()
}

object Router {
  val system = ActorSystem("MySystem")

  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 10)), "fetcherRouter")
  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "filterRouter")
  val reporter = system.actorOf(Props[Reporter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "reporterRouter")
}
