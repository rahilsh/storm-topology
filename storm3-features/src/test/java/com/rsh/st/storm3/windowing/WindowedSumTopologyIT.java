/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.storm3.windowing;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** End-to-end smoke test: the windowed-sum topology runs on a LocalCluster and shuts down. */
class WindowedSumTopologyIT {

  @Test
  void runsToCompletion() {
    assertDoesNotThrow(() -> WindowedSumTopology.main(new String[] {}));
  }
}
