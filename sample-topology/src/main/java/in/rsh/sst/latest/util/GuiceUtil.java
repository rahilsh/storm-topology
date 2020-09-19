package in.rsh.sst.latest.util;

import com.google.inject.Guice;
import com.google.inject.Injector;
import in.rsh.sst.latest.module.AppModule;
import lombok.extern.slf4j.Slf4j;

@Slf4j
//TODO: use Workerhook instead of this
public class GuiceUtil {

  private static final Injector injector = Guice.createInjector(new AppModule());

  private GuiceUtil() {}

  public static Injector getInjector() {
    return injector;
  }
}
