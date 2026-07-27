/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.st.topology.util;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.google.inject.Injector;
import com.rsh.st.topology.service.ExternalService;
import org.junit.jupiter.api.Test;

class GuiceUtilTest {

  @Test
  void exposesASingletonInjectorThatResolvesBindings() {
    Injector injector = GuiceUtil.getInjector();

    assertNotNull(injector);
    assertSame(injector, GuiceUtil.getInjector());
    assertNotNull(injector.getInstance(ExternalService.class));
  }
}
