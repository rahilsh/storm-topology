/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.basics.spout;

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

class GenerateNumberSpoutTest {

  @Test
  void emitsIncreasingEvenNumbers() {
    SpoutOutputCollector collector = mock(SpoutOutputCollector.class);
    GenerateNumberSpout spout = new GenerateNumberSpout();
    spout.open(null, null, collector);

    spout.nextTuple();
    spout.nextTuple();

    verify(collector).emit(new Values(2));
    verify(collector).emit(new Values(4));
  }

  @Test
  void declaresNumbersField() {
    OutputFieldsDeclarer declarer = mock(OutputFieldsDeclarer.class);

    new GenerateNumberSpout().declareOutputFields(declarer);

    ArgumentCaptor<Fields> captor = ArgumentCaptor.forClass(Fields.class);
    verify(declarer).declare(captor.capture());
    assertEquals(List.of("numbers"), captor.getValue().toList());
  }
}
