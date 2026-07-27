/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.windowing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.apache.storm.generated.StormTopology;
import org.junit.jupiter.api.Test;

class WindowedSumTopologyTest {

  @Test
  void buildsSpoutAndWindowedBolt() {
    StormTopology topology = WindowedSumTopology.build();

    assertEquals(1, topology.get_spouts_size());
    assertEquals(1, topology.get_bolts_size());
    assertTrue(topology.get_spouts().containsKey("numbers"));
    assertTrue(topology.get_bolts().containsKey("windowed-sum"));
  }
}
