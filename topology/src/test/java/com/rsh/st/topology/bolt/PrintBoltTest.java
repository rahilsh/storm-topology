/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology.bolt;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.google.inject.Guice;
import com.rsh.st.topology.hook.GuiceWorkerHook;
import com.rsh.st.topology.module.AppModule;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.junit.jupiter.api.Test;

class PrintBoltTest {

  @Test
  void consumesTuplesWithoutEmitting() {
    OutputCollector collector = mock(OutputCollector.class);
    // The injector is normally published by GuiceWorkerHook; supply it via the context.
    TopologyContext context = mock(TopologyContext.class);
    when(context.getResource(GuiceWorkerHook.INJECTOR_RESOURCE))
        .thenReturn(Guice.createInjector(new AppModule()));

    PrintBolt bolt = new PrintBolt();
    bolt.prepare(null, context, collector);

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
