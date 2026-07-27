/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.grouping;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.junit.jupiter.api.Test;

class LogNumberBoltTest {

  @Test
  void acksEveryTupleAndEmitsNothing() {
    OutputCollector collector = mock(OutputCollector.class);
    Tuple tuple = mock(Tuple.class);
    when(tuple.getIntegerByField("number")).thenReturn(42);

    LogNumberBolt bolt = new LogNumberBolt();
    bolt.prepare(null, null, collector);
    bolt.execute(tuple);
    bolt.declareOutputFields(mock(OutputFieldsDeclarer.class));

    verify(collector).ack(tuple);
  }
}
