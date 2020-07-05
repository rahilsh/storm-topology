package in.rsh.storm.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import in.rsh.storm.client.HttpClient;
import javax.inject.Named;

public class AppModule implements Module {

  @Override
  public void configure(Binder binder) {}

  @Provides
  @Singleton
  @Named("s")
  public String getS() {
    return "test1";
  }

  @Provides
  @Singleton
  public HttpClient getHttpClient() {
    return new HttpClient("something variable");
  }
}
