package shpider

import akka.actor.Actor
import org.jsoup.Jsoup
import org.jsoup.helper.Validate
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net._

// Filters links from given content
class SFStationFilter extends Filter {
  import ShpiderProtocol._

  val baseUrl = "http://www.sfstation.com/calendar/06-26-2013"

  def receive = {
    case Start => Router.fetcher ! FetchTask(baseUrl, self)

    case Content(link, html) => {
      val linkURL = new URL(link)
      val doc = Jsoup.parse(html)
      println("Parsed doc:")
      println(doc)

//      val filteredLinks = doc.select("a")
//        .select(":not(a[href*=" + linkURL.getHost() + "])")
//        .select(":not(a[href~=\\.(jpg|png|gif)])")
//        .select("a[href~=^https?://]").iterator()
//
//      while (filteredLinks.hasNext()) {
//        var href = filteredLinks.next().attr("href")
//        Router.fetcher ! FetchTask(href, self)
//      }
    }
    case e => handleUnknownEvent(e)
  }
}
