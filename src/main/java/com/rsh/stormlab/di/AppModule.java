/* SPDX-License-Identifier: Apache-2.0 */
package com.rsh.stormlab.di;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;

public class AppModule extends AbstractModule {

  @Override
  protected void configure() {
    bindConstant().annotatedWith(Names.named("dummyConstant")).to("Hello, Guice!");
  }

  @Provides
  @Singleton
  public DummyClass getDummyInstance() {
    return new DummyClass("something variable");
  }
}
