/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.trident;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

/** End-to-end smoke test that runs the DRPC word-count topology and checks its output. */
class WordCountTridentDRPCIT {

  @Test
  void answersDrpcQueries() throws Exception {
    PrintStream originalOut = System.out;
    ByteArrayOutputStream captured = new ByteArrayOutputStream();
    System.setOut(new PrintStream(captured, true, StandardCharsets.UTF_8));
    try {
      assertDoesNotThrow(() -> WordCountTridentDRPC.main(new String[] {}));
    } finally {
      System.setOut(originalOut);
    }

    String output = captured.toString(StandardCharsets.UTF_8);
    assertTrue(output.contains("countNoOfOccurrence:"), output);
    assertTrue(output.contains("totalWords: [[3]]"), output);
  }
}
