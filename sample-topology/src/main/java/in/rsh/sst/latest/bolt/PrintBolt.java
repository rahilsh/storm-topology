package in.rsh.sst.latest.bolt;

import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import in.rsh.sst.latest.service.ExternalService;
import in.rsh.sst.latest.util.GuiceUtil;
import java.util.Map;
import java.util.TreeMap;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

@Slf4j
public class PrintBolt extends BaseRichBolt {

  private String dummyConstant;
  private ExternalService externalService;

  private Map<Integer, Integer> numsq = null;

  public void prepare(
      Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
    numsq = new TreeMap<>();
    final Injector injector = GuiceUtil.getInjector();
    this.externalService = injector.getInstance(ExternalService.class);
    this.dummyConstant = injector.getInstance(Key.get(String.class, Names.named("dummyConstant")));
  }

  public void execute(Tuple tuple) {
    final Integer number = tuple.getIntegerByField("numbers");
    final Integer square = tuple.getIntegerByField("numbersquare");
    log.info("dummyConstant=" + dummyConstant);
    log.info("externalService.getSomething()=" + externalService.getSomething());
    numsq.put(number, square);
  }

  @Override
  public void cleanup() {
    log.info("Result");
    numsq.forEach((k, v) -> log.info("{} : {}", k, v));
    super.cleanup();
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {}
}
