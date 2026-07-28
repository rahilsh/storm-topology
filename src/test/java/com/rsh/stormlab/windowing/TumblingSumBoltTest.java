/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.windowing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.apache.storm.windowing.TupleWindow;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

class TumblingSumBoltTest {

  @Test
  void emitsSumOfNumbersInWindow() {
    Tuple a = mock(Tuple.class);
    Tuple b = mock(Tuple.class);
    when(a.getIntegerByField("number")).thenReturn(3);
    when(b.getIntegerByField("number")).thenReturn(7);
    TupleWindow window = mock(TupleWindow.class);
    when(window.get()).thenReturn(List.of(a, b));
    OutputCollector collector = mock(OutputCollector.class);

    TumblingSumBolt bolt = new TumblingSumBolt();
    bolt.prepare(null, null, collector);
    bolt.execute(window);

    verify(collector).emit(new Values(10L));
  }

  @Test
  void declaresSumField() {
    OutputFieldsDeclarer declarer = mock(OutputFieldsDeclarer.class);

    new TumblingSumBolt().declareOutputFields(declarer);

    ArgumentCaptor<Fields> captor = ArgumentCaptor.forClass(Fields.class);
    verify(declarer).declare(captor.capture());
    assertEquals(List.of("sum"), captor.getValue().toList());
  }
}
