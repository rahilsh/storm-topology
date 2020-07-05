package in.rsh.storm;

import com.google.inject.Injector;
import in.rsh.storm.bolt.PrintBolt;
import in.rsh.storm.bolt.SquareBolt;
import in.rsh.storm.spout.GenerateNumberSpout;
import in.rsh.storm.util.GuiceUtil;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

public class Main {
  public static void main(String[] args) throws Exception {
    System.out.println("Hello storm");
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
