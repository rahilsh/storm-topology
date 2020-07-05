package in.rsh.storm.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import in.rsh.storm.module.AppModule;

public class GuiceUtil {

  private static Injector injector;

  public static synchronized Injector getInjector() {
    if (injector == null) {
      System.out.println("creating injector");
      injector = Guice.createInjector(new AppModule());
    }
    return injector;
  }
}
