package shpider

import akka.actor.Actor
import org.jsoup.Jsoup
import org.jsoup.helper.Validate
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import java.net._

// Filters links from given content
class Filter extends Actor {
  val fetcher = Router.fetcher

  def receive = {
    case (link: String, html: String) => {
      val linkURL = new URL(link)
      val doc = Jsoup.parse(html)
      val filteredLinks = doc.select("a")
        .select(":not(a[href*=" + linkURL.getHost() + "])")
        .select(":not(a[href~=\\.(jpg|png|gif)])")
        .select("a[href~=^https?://]").iterator()

      while (filteredLinks.hasNext()) {
        var href = filteredLinks.next().attr("href")
        fetcher ! href
      }
    }
  }
}
