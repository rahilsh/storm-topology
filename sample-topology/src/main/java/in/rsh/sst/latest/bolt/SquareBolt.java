package in.rsh.sst.latest.bolt;

import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

public class SquareBolt extends BaseRichBolt {

  private OutputCollector outputCollector;

  public void prepare(
      Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
    this.outputCollector = outputCollector;
  }

  public void execute(Tuple tuple) {
    Integer i = tuple.getIntegerByField("numbers");
    Integer square = i * i;
    this.outputCollector.emit(new Values(i, square));
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("numbers", "numbersquare"));
  }
}
