package shpider

import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter

object Shpider extends App {
  import ShpiderProtocol._

  println("Starting shpider ...")

  val system = ActorSystem("MySystem")
  system.actorOf(Props[SFStationFilter], "SFStationFilter") ! Start
}
