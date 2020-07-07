package in.rsh.sst.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import in.rsh.sst.client.HttpClient;
import javax.inject.Named;

public class AppModule implements Module {

  @Override
  public void configure(Binder binder) {}

  @Provides
  @Singleton
  @Named("dummyConstant")
  public String getDummyConstant() {
    return "dummyValue";
  }

  @Provides
  @Singleton
  public HttpClient getHttpClient() {
    return new HttpClient("something variable");
  }
}
