/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology;

import com.rsh.st.topology.bolt.PrintBolt;
import com.rsh.st.topology.bolt.SquareBolt;
import com.rsh.st.topology.hook.GuiceWorkerHook;
import com.rsh.st.topology.spout.GenerateNumberSpout;
import lombok.extern.slf4j.Slf4j;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;

@Slf4j
public class Main {
  public static void main(String[] args) throws Exception {
    log.info("Hello storm!");
    final TopologyBuilder topologyBuilder = new TopologyBuilder();
    // Publishes a Guice injector to every worker; PrintBolt reads it in prepare().
    topologyBuilder.addWorkerHook(new GuiceWorkerHook());
    topologyBuilder.setSpout("number_spout", new GenerateNumberSpout());
    topologyBuilder.setBolt("square_bolt", new SquareBolt()).shuffleGrouping("number_spout");
    topologyBuilder.setBolt("print_bolt", new PrintBolt()).shuffleGrouping("square_bolt");
    final StormTopology topology = topologyBuilder.createTopology();
    Config config = new Config();
    try (LocalCluster cluster = new LocalCluster()) {
      cluster.submitTopology("square_topology", config, topology);
      Thread.sleep(10000);
      cluster.killTopology("square_topology");
    }
  }
}
