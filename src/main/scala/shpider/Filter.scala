package shpider

import akka.actor.Actor
import org.jsoup.Jsoup
import org.jsoup.helper.Validate
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net._

abstract class Filter extends Actor with ActorEnhancements {
  import ShpiderProtocol._

}
