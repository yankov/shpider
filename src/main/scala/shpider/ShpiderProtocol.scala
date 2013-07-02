package shpider

import akka.actor.ActorRef

object ShpiderProtocol {
  case class Content(link: String, html: String)
  case object Start
  case class FetchTask(link: String, filter: ActorRef)

}
