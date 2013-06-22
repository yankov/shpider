package shpider

import dispatch._
import akka.actor.Actor

// Fetches the content of the given URL
class Fetcher extends Actor with ActorEnhancements with akka.actor.ActorLogging {

  def receive = {
    case link: String => {
      val docFuture = Http(url(link) OK as.String).either

      for (doc <- docFuture) {
        doc match {
          case Right(content) => {
            println(link)
            Router.filter ! (link, content)
            Router.reporter ! link
          }
          case Left(StatusCode(code)) => {}
        }
      }
    }
    case e => handleUnknownEvent(e)
  }
}
