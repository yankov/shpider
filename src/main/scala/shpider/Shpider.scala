package shpider

import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter

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
