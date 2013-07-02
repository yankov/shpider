package shpider

import dispatch._
import akka.actor.Actor
import java.io.IOException
import scala.concurrent.ExecutionContext.Implicits.global   // why do i need that for dispatch 0.10?

// Fetches the content of the given URL
class Fetcher extends Actor with ActorEnhancements with akka.actor.ActorLogging  {
  import ShpiderProtocol._

  def receive = {
    case FetchTask(link, filter) => {
      try {
        val docFuture = Http(url(link) > as.String).either

        for (doc <- docFuture) {
          doc match {
            case Right(content) => {
              println(link)
              filter ! Content(link, content)
              Router.reporter ! link
            }

            case Left(StatusCode(code)) => { println("HTTP exception")}
          }
        }
      }
      catch {
        case _: Throwable =>  println("Unknown exception has been caught")
      }
    }
    case e => handleUnknownEvent(e)
  }
}
