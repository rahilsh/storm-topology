/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.tridentdrpc.function;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.apache.storm.trident.operation.TridentCollector;
import org.apache.storm.trident.tuple.TridentTuple;
import org.apache.storm.tuple.Values;
import org.junit.jupiter.api.Test;

class CSVSplitTest {

  @Test
  void emitsEachTokenAndSkipsEmptyOnes() {
    TridentTuple tuple = mock(TridentTuple.class);
    when(tuple.getString(0)).thenReturn("a,b,,c");
    TridentCollector collector = mock(TridentCollector.class);

    new CSVSplit().execute(tuple, collector);

    verify(collector).emit(new Values("a"));
    verify(collector).emit(new Values("b"));
    verify(collector).emit(new Values("c"));
    verifyNoMoreInteractions(collector);
  }
}
