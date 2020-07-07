package in.rsh.sst;

import com.google.inject.Injector;
import in.rsh.sst.bolt.PrintBolt;
import in.rsh.sst.bolt.SquareBolt;
import in.rsh.sst.spout.GenerateNumberSpout;
import in.rsh.sst.util.GuiceUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    log.info("Hello storm");
    final Injector injector = GuiceUtil.getInjector();
    final TopologyBuilder topologyBuilder = new TopologyBuilder();
    topologyBuilder.setSpout("number_spout", new GenerateNumberSpout());
    topologyBuilder.setBolt("square_bolt", new SquareBolt()).shuffleGrouping("number_spout");
    topologyBuilder
        .setBolt("print_bolt", injector.getInstance(PrintBolt.class))
        .shuffleGrouping("square_bolt");
    final StormTopology topology = topologyBuilder.createTopology();
    Config config = new Config();
    try (LocalCluster cluster = new LocalCluster()) {
      cluster.submitTopology("square_topology", config, topology);
      Thread.sleep(10000);
      cluster.killTopology("square_topology");
      cluster.shutdown();
    }
  }
}
