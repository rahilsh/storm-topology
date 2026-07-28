/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.common;

import java.util.Map;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.apache.storm.utils.Utils;

/** Emits a monotonically increasing sequence of integers on the {@code number} field. */
public class SequentialNumberSpout extends BaseRichSpout {

  private static final long serialVersionUID = 1L;

  private transient SpoutOutputCollector collector;
  private int next = 1;

  @Override
  public void open(Map<String, Object> conf, TopologyContext context, SpoutOutputCollector col) {
    this.collector = col;
  }

  @Override
  public void nextTuple() {
    Utils.sleep(50);
    collector.emit(new Values(next++));
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    declarer.declare(new Fields("number"));
  }
}
