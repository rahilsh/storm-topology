/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.config;

import org.apache.storm.Config;

/**
 * Config builders that demonstrate the transport-layer features introduced in Apache Storm 3.0.
 *
 * <p>These are opt-in and configured entirely through the topology {@link Config}; no code changes
 * to spouts or bolts are required.
 */
public final class Storm3Configs {

  private Storm3Configs() {}

  /**
   * Enables Zstd compression for inter-worker tuple traffic (Storm 3.0+). Tuples whose serialized
   * size exceeds {@code thresholdBytes} are compressed at the given Zstd {@code level}, reducing
   * network bandwidth between workers.
   *
   * @param zstdLevel Zstd compression level (higher = smaller but more CPU)
   * @param thresholdBytes only compress tuples larger than this many bytes
   */
  public static Config zstdTupleCompression(int zstdLevel, int thresholdBytes) {
    Config conf = new Config();
    conf.put(Config.TOPOLOGY_TUPLE_COMPRESSION_ENABLE, true);
    conf.put(Config.TOPOLOGY_TUPLE_COMPRESSION_THRESHOLD, thresholdBytes);
    conf.put(Config.STORM_COMPRESSION_ZSTD_LEVEL, zstdLevel);
    return conf;
  }

  /**
   * Enables AIMD-based dynamic producer batch sizing (Storm 3.0+). Producer batch sizes in the
   * internal {@code JCQueue} adapt under backpressure using additive-increase / multiplicative-
   * decrease, improving throughput without manual tuning.
   */
  public static Config dynamicProducerBatching() {
    Config conf = new Config();
    conf.put(Config.TOPOLOGY_PRODUCER_BATCH_DYNAMIC, true);
    return conf;
  }
}
