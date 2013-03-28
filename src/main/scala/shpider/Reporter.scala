package shpider

import kafka.producer.{ Producer, ProducerConfig, ProducerData }
import akka.actor.Actor
import java.util.{ Properties, ArrayList }

// Keeps stats and matches
class Reporter extends Actor with ActorEnhancements {
  val topic = "urls"
  val props = new Properties()
  props.put("broker.list", "0:localhost:9092")
  props.put("serializer.class", "kafka.serializer.StringEncoder")
  val config = new ProducerConfig(props)
  val producer = new Producer[String, String](config)

  def receive = {
    case href: String => {
      //      var messages = new ArrayList[String]()
      //      messages.add(href)

      val data = new ProducerData[String, String](topic, href)
      producer.send(data)
    }
    case e => handleUnknownEvent(e)
  }
}
