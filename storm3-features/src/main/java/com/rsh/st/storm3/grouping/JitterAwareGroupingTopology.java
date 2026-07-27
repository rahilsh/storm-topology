/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.grouping;

import com.rsh.st.storm3.spout.SequentialNumberSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.grouping.JitterAwareStreamGrouping;
import org.apache.storm.topology.TopologyBuilder;

/**
 * Demonstrates {@link JitterAwareStreamGrouping}, new in Apache Storm 3.0. Instead of {@code
 * shuffleGrouping}, tuples are routed with {@code customGrouping(..., new
 * JitterAwareStreamGrouping())}, which steers work away from high-jitter tasks using an EWMA jitter
 * metric.
 */
public final class JitterAwareGroupingTopology {

  /** Number of downstream tasks the grouping distributes across. */
  public static final int PARALLELISM = 3;

  private JitterAwareGroupingTopology() {}

  /** Builds the topology: {@code numbers -> printer} using a jitter-aware grouping. */
  public static StormTopology build() {
    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("numbers", new SequentialNumberSpout());
    builder
        .setBolt("printer", new LogNumberBolt(), PARALLELISM)
        .customGrouping("numbers", new JitterAwareStreamGrouping());
    return builder.createTopology();
  }

  public static void main(String[] args) throws Exception {
    Config conf = new Config();
    try (LocalCluster cluster = new LocalCluster()) {
      cluster.submitTopology("jitter-aware-grouping", conf, build());
      Thread.sleep(10000);
      cluster.killTopology("jitter-aware-grouping");
    }
  }
}
