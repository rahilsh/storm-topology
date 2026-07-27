/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.rsh.st.topology.client.DummyClass;
import org.junit.jupiter.api.Test;

class ExternalServiceTest {

  @Test
  void returnsValueFromInjectedDummyClass() {
    ExternalService service = new ExternalService(new DummyClass("hello"));

    assertEquals("hello", service.getSomething());
  }
}
