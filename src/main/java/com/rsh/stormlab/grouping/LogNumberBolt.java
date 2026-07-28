/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.grouping;

import java.util.Map;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Terminal bolt that logs each received number. Used to demonstrate stream groupings. */
public class LogNumberBolt extends BaseRichBolt {

  private static final long serialVersionUID = 1L;
  private static final Logger LOG = LoggerFactory.getLogger(LogNumberBolt.class);

  private transient OutputCollector collector;

  @Override
  public void prepare(Map<String, Object> conf, TopologyContext context, OutputCollector col) {
    this.collector = col;
  }

  @Override
  public void execute(Tuple tuple) {
    LOG.info("received number: {}", tuple.getIntegerByField("number"));
    collector.ack(tuple);
  }

  @Override
  public void declareOutputFields(OutputFieldsDeclarer declarer) {
    // Terminal bolt: emits nothing.
  }
}
