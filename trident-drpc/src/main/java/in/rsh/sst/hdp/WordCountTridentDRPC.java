package in.rsh.sst.hdp;

import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.LocalDRPC;
import org.apache.storm.trident.TridentTopology;
import org.apache.storm.trident.operation.builtin.Count;
import org.apache.storm.trident.operation.builtin.Debug;
import org.apache.storm.trident.operation.builtin.Sum;
import org.apache.storm.trident.testing.Split;
import org.apache.storm.tuple.Fields;

public class WordCountTridentDRPC {

  public static void main(String[] args) {
    TridentTopology topology = new TridentTopology();
    LocalDRPC drpc = new LocalDRPC();

    topology
        .newDRPCStream("countNoOfOccurrence", drpc)
        .each(new Fields("args"), new Split(), new Fields("word"))
        .each(new Fields("word"), new Debug())
        .groupBy(new Fields("word"))
        .aggregate(new Fields("word"), new Count(), new Fields("count"))
        .parallelismHint(9);

    topology
        .newDRPCStream("totalWords", drpc)
        .each(new Fields("args"), new Split(), new Fields("word"))
        .each(new Fields("word"), new Debug())
        .parallelismHint(3)
        .groupBy(new Fields("word"))
        .aggregate(new Fields("word"), new Count(), new Fields("count"))
        .aggregate(new Fields("count"), new Sum(), new Fields("sum"));

    Config conf = new Config();
    LocalCluster cluster = new LocalCluster();
    cluster.submitTopology("trident", conf, topology.build());
    final String input = "cat dog man";
    System.out.println("countNoOfOccurrence: " + drpc.execute("countNoOfOccurrence", input));
    System.out.println("totalWords: " + drpc.execute("totalWords", input));

    cluster.shutdown();
    drpc.shutdown();
  }
}
