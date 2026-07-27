/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology.bolt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.junit.jupiter.api.Test;

class PrintBoltTest {

  @Test
  void consumesTuplesWithoutEmitting() {
    OutputCollector collector = mock(OutputCollector.class);
    PrintBolt bolt = new PrintBolt();
    // prepare() resolves collaborators from the Guice injector; this exercises
    // the dependency-injection wiring.
    bolt.prepare(null, null, collector);

    Tuple tuple = mock(Tuple.class);
    when(tuple.getIntegerByField("numbers")).thenReturn(2);
    when(tuple.getIntegerByField("numbersquare")).thenReturn(4);
    bolt.execute(tuple);

    bolt.cleanup();
    bolt.declareOutputFields(mock(OutputFieldsDeclarer.class));

    // PrintBolt is a terminal bolt: it never emits downstream.
    verifyNoInteractions(collector);
  }
}
