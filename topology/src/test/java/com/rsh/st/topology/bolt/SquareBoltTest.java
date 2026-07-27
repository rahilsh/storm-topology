/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology.bolt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.junit.jupiter.api.Test;

class SquareBoltTest {

  @Test
  void emitsNumberAndItsSquare() {
    OutputCollector collector = mock(OutputCollector.class);
    Tuple tuple = mock(Tuple.class);
    when(tuple.getIntegerByField("numbers")).thenReturn(5);

    SquareBolt bolt = new SquareBolt();
    bolt.prepare(null, null, collector);
    bolt.execute(tuple);

    verify(collector).emit(new Values(5, 25));
  }
}
