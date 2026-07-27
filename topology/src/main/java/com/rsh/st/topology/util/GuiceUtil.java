package com.rsh.st.topology.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.rsh.st.topology.module.AppModule;
import lombok.extern.slf4j.Slf4j;

// Single process-wide Guice injector shared across bolts. Works in local mode;
// see the "Dependency injection in a bolt" section of the README for the
// distributed-mode caveat. TODO: consider a Storm WorkerHook.
@Slf4j
public class GuiceUtil {

  private static final Injector injector = Guice.createInjector(new AppModule());

  private GuiceUtil() {}

  public static Injector getInjector() {
    return injector;
  }
}
