/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.config;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.apache.storm.Config;
import org.junit.jupiter.api.Test;

class Storm3ConfigsTest {

  @Test
  void enablesZstdTupleCompression() {
    Config conf = Storm3Configs.zstdTupleCompression(3, 1024);

    assertEquals(Boolean.TRUE, conf.get(Config.TOPOLOGY_TUPLE_COMPRESSION_ENABLE));
    assertEquals(1024, conf.get(Config.TOPOLOGY_TUPLE_COMPRESSION_THRESHOLD));
    assertEquals(3, conf.get(Config.STORM_COMPRESSION_ZSTD_LEVEL));
  }

  @Test
  void enablesDynamicProducerBatching() {
    Config conf = Storm3Configs.dynamicProducerBatching();

    assertEquals(Boolean.TRUE, conf.get(Config.TOPOLOGY_PRODUCER_BATCH_DYNAMIC));
  }
}
