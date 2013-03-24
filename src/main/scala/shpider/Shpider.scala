package shpider

import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter

object Shpider extends App {
  val system = ActorSystem("MySystem")

  Router.fetcher ! "http://techcrunch.com"
}

object Router {
  val system = ActorSystem("MySystem")

  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 100)), "fetcherRouter")
  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 100)), "filterRouter")
  val reporter = system.actorOf(Props[Reporter].withRouter(RoundRobinRouter(nrOfInstances = 100)), "reporterRouter")
}