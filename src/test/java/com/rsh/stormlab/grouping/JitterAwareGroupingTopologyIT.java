/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.grouping;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** End-to-end smoke test: the jitter-aware grouping topology runs on a LocalCluster. */
class JitterAwareGroupingTopologyIT {

  @Test
  void runsToCompletion() {
    assertDoesNotThrow(() -> JitterAwareGroupingTopology.main(new String[] {}));
  }
}
