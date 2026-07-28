/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.windowing;

import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseWindowedBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;

/**
 * Sums the {@code number} field of every tuple in each window and emits the total on the {@code
 * sum} field. Use with a tumbling or sliding window, e.g. {@code withTumblingWindow(Count.of(5))}.
 */
public class TumblingSumBolt extends BaseWindowedBolt {

  private static final long serialVersionUID = 1L;

  private transient OutputCollector collector;

  @Override
  public void prepare(Map<String, Object> conf, TopologyContext context, OutputCollector col) {
    this.collector = col;
  }

  @Override
  public void execute(TupleWindow window) {
    long sum = 0;
    for (Tuple tuple : window.get()) {
      sum += tuple.getIntegerByField("number");
    }
    collector.emit(new Values(sum));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("sum"));
  }
}
