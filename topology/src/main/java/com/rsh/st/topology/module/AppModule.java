package com.rsh.st.topology.module;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.name.Names;
import com.rsh.st.topology.client.DummyClass;

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
