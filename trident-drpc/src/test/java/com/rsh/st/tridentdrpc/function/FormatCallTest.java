package com.rsh.st.tridentdrpc.function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.junit.jupiter.api.Test;

class FormatCallTest {

  @Test
  void formatsFromAndToNumbers() {
    TridentTuple tuple = mock(TridentTuple.class);
    when(tuple.getString(0)).thenReturn("111");
    when(tuple.getString(1)).thenReturn("222");
    TridentCollector collector = mock(TridentCollector.class);

    new FormatCall().execute(tuple, collector);

    verify(collector).emit(new Values("111 - 222"));
  }
}
