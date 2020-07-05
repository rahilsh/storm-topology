package in.rsh.storm.spout;

import java.util.Map;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

public class GenerateNumberSpout extends BaseRichSpout {

  private SpoutOutputCollector spoutOutputCollector;
  private Integer i = 2;

  public void open(
      Map<String, Object> map,
      TopologyContext topologyContext,
      SpoutOutputCollector spoutOutputCollector) {
    this.spoutOutputCollector = spoutOutputCollector;
  }

  public void nextTuple() {
    Utils.sleep(100);
    this.spoutOutputCollector.emit(new Values(i));
    i += 2;
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    outputFieldsDeclarer.declare(new Fields("numbers"));
  }
}
