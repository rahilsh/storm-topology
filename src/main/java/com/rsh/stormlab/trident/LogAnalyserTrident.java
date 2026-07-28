/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.trident;

import com.rsh.stormlab.trident.function.CSVSplit;
import com.rsh.stormlab.trident.function.FormatCall;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentState;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.trident.operation.builtin.FilterNull;
import org.apache.storm.trident.operation.builtin.MapGet;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.FeederBatchSpout;
import org.apache.storm.trident.testing.MemoryMapState.Factory;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

public class LogAnalyserTrident {
  public static void main(String[] args) throws Exception {
    System.out.println("Log Analyser Trident");
    TridentTopology topology = new TridentTopology();

    FeederBatchSpout testSpout =
        new FeederBatchSpout(List.of("fromMobileNumber", "toMobileNumber", "duration"));

    TridentState callCounts =
        topology
            .newStream("fixed-batch-spout", testSpout)
            .each(
                new Fields("fromMobileNumber", "toMobileNumber"),
                new FormatCall(),
                new Fields("call"))
            .groupBy(new Fields("call"))
            .persistentAggregate(new Factory(), new Count(), new Fields("count"));

    try (LocalDRPC drpc = new LocalDRPC()) {
      topology
          .newDRPCStream("call_count", drpc)
          .stateQuery(callCounts, new Fields("args"), new MapGet(), new Fields("count"));

      topology
          .newDRPCStream("multiple_call_count", drpc)
          .each(new Fields("args"), new CSVSplit(), new Fields("call"))
          .groupBy(new Fields("call"))
          .stateQuery(callCounts, new Fields("call"), new MapGet(), new Fields("count"))
          .each(new Fields("call", "count"), new Debug())
          .each(new Fields("count"), new FilterNull())
          .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

      Config conf = new Config();
      try (LocalCluster cluster = new LocalCluster()) {
        cluster.submitTopology("trident", conf, topology.build());
        ThreadLocalRandom randomGenerator = ThreadLocalRandom.current();
        int idx = 0;

        while (idx < 10) {
          testSpout.feed(
              List.of(new Values("1234123401", "1234123402", randomGenerator.nextInt(60))));

          testSpout.feed(
              List.of(new Values("1234123401", "1234123403", randomGenerator.nextInt(60))));

          testSpout.feed(
              List.of(new Values("1234123401", "1234123404", randomGenerator.nextInt(60))));

          testSpout.feed(
              List.of(new Values("1234123402", "1234123403", randomGenerator.nextInt(60))));

          idx = idx + 1;
        }

        System.out.println("DRPC : Query starts");
        System.out.println(drpc.execute("call_count", "1234123401 - 1234123402"));
        System.out.println(
            drpc.execute("multiple_call_count", "1234123401 - 1234123402,1234123401 - 1234123403"));
        System.out.println("DRPC : Query ends");
      }
    }
  }
}
