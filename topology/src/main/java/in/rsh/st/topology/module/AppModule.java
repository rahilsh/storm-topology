package in.rsh.st.topology.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import in.rsh.st.topology.client.DummyClass;
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
  public DummyClass getDummyInstance() {
    return new DummyClass("something variable");
  }
}
