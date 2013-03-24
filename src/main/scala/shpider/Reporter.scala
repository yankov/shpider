package shpider

import redis.clients.jedis._
import akka.actor.Actor

// Keeps stats and matches
class Reporter extends Actor with ActorEnhancements {
  val redis = new Jedis("localhost")

  def receive = {
    case href: String => {
      redis.incr("shpider:counter")
    }
    case e => handleUnknownEvent(e)
  }
}
