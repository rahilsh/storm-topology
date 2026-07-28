/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.List;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class SequentialNumberSpoutTest {

  @Test
  void emitsAscendingIntegers() {
    SpoutOutputCollector collector = mock(SpoutOutputCollector.class);
    SequentialNumberSpout spout = new SequentialNumberSpout();
    spout.open(null, null, collector);

    spout.nextTuple();
    spout.nextTuple();

    verify(collector).emit(new Values(1));
    verify(collector).emit(new Values(2));
  }

  @Test
  void declaresNumberField() {
    OutputFieldsDeclarer declarer = mock(OutputFieldsDeclarer.class);

    new SequentialNumberSpout().declareOutputFields(declarer);

    ArgumentCaptor<Fields> captor = ArgumentCaptor.forClass(Fields.class);
    verify(declarer).declare(captor.capture());
    assertEquals(List.of("number"), captor.getValue().toList());
  }
}
