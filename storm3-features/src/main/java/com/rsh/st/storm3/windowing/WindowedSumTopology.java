/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.windowing;

import com.rsh.st.storm3.config.Storm3Configs;
import com.rsh.st.storm3.spout.SequentialNumberSpout;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.topology.base.BaseWindowedBolt.Count;

/**
 * Tumbling-window aggregation: numbers are grouped into fixed-size windows and summed. Also
 * demonstrates enabling Storm 3.0 Zstd tuple compression via {@link Storm3Configs}.
 */
public final class WindowedSumTopology {

  /** Number of tuples per tumbling window. */
  public static final int WINDOW_SIZE = 5;

  private WindowedSumTopology() {}

  /** Builds the topology: {@code numbers -> windowed-sum} with a tumbling window. */
  public static StormTopology build() {
    TopologyBuilder builder = new TopologyBuilder();
    builder.setSpout("numbers", new SequentialNumberSpout());
    builder
        .setBolt("windowed-sum", new TumblingSumBolt().withTumblingWindow(Count.of(WINDOW_SIZE)))
        .shuffleGrouping("numbers");
    return builder.createTopology();
  }

  public static void main(String[] args) throws Exception {
    Config conf = Storm3Configs.zstdTupleCompression(3, 1024);
    try (LocalCluster cluster = new LocalCluster()) {
      cluster.submitTopology("windowed-sum", conf, build());
      Thread.sleep(10000);
      cluster.killTopology("windowed-sum");
    }
  }
}
