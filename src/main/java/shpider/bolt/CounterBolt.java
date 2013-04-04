package shpider.bolt;

import java.util.Map;

import redis.clients.jedis.Jedis;
import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

public class CounterBolt extends BaseRichBolt {
	OutputCollector _collector;
	Jedis redis;

	@Override
	public void prepare(Map conf, TopologyContext context,
			OutputCollector collector) {
		_collector = collector;
		redis = new Jedis("localhost");
	}

	@Override
	public void execute(Tuple tuple) {
		redis.incr("urls:counter");

		redis.sadd("urls", tuple.getValue(0).toString());
		_collector.emit(tuple, new Values(tuple));
		_collector.ack(tuple);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("url"));
	}

}