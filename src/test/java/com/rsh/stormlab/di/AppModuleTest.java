/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.di;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;
import org.junit.jupiter.api.Test;

class AppModuleTest {

  private final Injector injector = Guice.createInjector(new AppModule());

  @Test
  void bindsNamedDummyConstant() {
    String value = injector.getInstance(Key.get(String.class, Names.named("dummyConstant")));

    assertEquals("Hello, Guice!", value);
  }

  @Test
  void providesDummyClassSingleton() {
    DummyClass first = injector.getInstance(DummyClass.class);
    DummyClass second = injector.getInstance(DummyClass.class);

    assertEquals("something variable", first.something());
    assertEquals(first, second);
  }
}
