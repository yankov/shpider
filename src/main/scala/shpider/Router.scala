package shpider

import akka.actor.{Props, ActorSystem}
import akka.routing.RoundRobinRouter

object Router {
  val system = ActorSystem("MySystem")

  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 10)), "fetcherRouter")
  //  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "filterRouter")
  val reporter = system.actorOf(Props[Reporter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "reporterRouter")
}
