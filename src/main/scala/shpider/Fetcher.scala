package shpider

import dispatch._
import akka.actor.Actor

// Fetches the content of the given URL
class Fetcher extends Actor {
  val filter = Router.filter
  val reporter = Router.reporter

  def receive = {
    case link: String => {
      val docFuture = Http(url(link) OK as.String).either

      for (doc <- docFuture) {
        doc match {
          case Right(content) => {
            filter ! (link, content)
            reporter ! link
          }
          case Left(StatusCode(code)) => {}
        }
      }
    }
  }
}
