package shpider.bolt

import backtype.storm.task.OutputCollector
import backtype.storm.task.TopologyContext
import backtype.storm.topology.OutputFieldsDeclarer
import backtype.storm.topology.base.BaseRichBolt
import backtype.storm.tuple.Fields
import backtype.storm.tuple.Tuple
import backtype.storm.tuple.Values
import java.util.Map

class CountBolt extends BaseRichBolt {
  var _collector: OutputCollector = _

  override def prepare(conf: Map[_, _], context: TopologyContext, collector: OutputCollector) {
    _collector = collector
  }

  override def execute(tuple: Tuple) {
    _collector.emit(tuple, new Values(tuple.getString(0) + "!!!"))
    _collector.ack(tuple)
  }

  override def declareOutputFields(declarer: OutputFieldsDeclarer) {
    declarer.declare(new Fields("word"))
  }

}
