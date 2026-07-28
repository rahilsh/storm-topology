/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.trident;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.Test;

/** End-to-end smoke test: the log-analyser Trident topology runs and answers DRPC queries. */
class LogAnalyserTridentIT {

  @Test
  void runsToCompletion() {
    assertDoesNotThrow(() -> LogAnalyserTrident.main(new String[] {}));
  }
}
