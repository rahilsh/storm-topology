package in.rsh.storm.bolt;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import in.rsh.storm.service.ExternalService;
import in.rsh.storm.util.GuiceUtil;
import java.util.Map;
import java.util.TreeMap;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

public class PrintBolt extends BaseRichBolt {

  private String s;
  private ExternalService externalService;

  private Map<Integer, Integer> numsq = null;

  public void prepare(
      Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
    numsq = new TreeMap<>();
    final Injector injector = GuiceUtil.getInjector();
    this.externalService = injector.getInstance(ExternalService.class);
    this.s = injector.getInstance(Key.get(String.class, Names.named("s")));
  }

  public void execute(Tuple tuple) {
    final Integer number = tuple.getIntegerByField("numbers");
    final Integer square = tuple.getIntegerByField("numbersquare");
    System.out.println("s=" + s);
    System.out.println("externalService.getSomething()=" + externalService.getSomething());
    numsq.put(number, square);
  }

  @Override
  public void cleanup() {
    System.out.println("Result");
    numsq.forEach((k, v) -> System.out.printf("%s : %s %n", k, v));
    super.cleanup();
  }

  public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {}
}
