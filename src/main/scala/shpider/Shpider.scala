package shpider

import redis.clients.jedis._
import dispatch._
import java.net._
import java.io._
import org.jsoup.Jsoup
import org.jsoup.helper.Validate
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorSystem
import akka.routing.RoundRobinRouter

object Shpider extends App {
  val system = ActorSystem("MySystem")
  
  Router.fetcher ! "http://techcrunch.com"  
}

object Router {
  val system    = ActorSystem("MySystem")
  
  val fetcher = system.actorOf(Props[Fetcher].withRouter(RoundRobinRouter(nrOfInstances = 10)), "fetcherRouter")
  val filter = system.actorOf(Props[Filter].withRouter(RoundRobinRouter(nrOfInstances = 10)), "filterRouter")
  val reporter  = system.actorOf(Props[Reporter], name = "reporterActor")  
}


class Fetcher extends Actor {
  val filter = Router.filter 
  
  def receive = {
    case link:String  => {
      val docFuture = Http( url(link) OK as.String).either
     
      for (doc <- docFuture) {
        doc match {
		  case Right(content)         => filter ! (link, content)
		  case Left(StatusCode(code)) => {}
        }
      }    	 
    }
  }
}

class Filter extends Actor {
  val fetcher = Router.fetcher
  val reporter = Router.reporter
  
  def receive = {
    case (link:String, html:String) => {
      val linkURL = new URL(link)
      val doc = Jsoup.parse(html)
      val filteredLinks = doc.select("a")
      						 .select(":not(a[href*="+ linkURL.getHost() +"])")
			    			 .select(":not(a[href~=\\.(jpg|png|gif)])")
			    			 .select("a[href~=^https?://]").iterator()
    			  
      while(filteredLinks.hasNext()) {
    	 var href = filteredLinks.next().attr("href")
    	 reporter ! href
         fetcher  ! href
      }
    }
  }
}

class Reporter extends Actor {
  val redis = new Jedis("localhost")
  
  def receive = {
    case href:String => {
      redis.incr("shpider:counter")
    }
  } 
} 
