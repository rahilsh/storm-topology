/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** End-to-end smoke test: the topology runs on a LocalCluster and shuts down cleanly. */
class MainIT {

  @Test
  void runsTopologyToCompletion() {
    assertDoesNotThrow(() -> Main.main(new String[] {}));
  }
}
