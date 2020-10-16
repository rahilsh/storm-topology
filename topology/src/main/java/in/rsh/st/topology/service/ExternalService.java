package in.rsh.st.topology.service;

import com.google.inject.Inject;
import in.rsh.st.topology.client.DummyClass;

public class ExternalService {

  private final DummyClass dummyClass;

  @Inject
  public ExternalService(DummyClass dummyClass) {
    this.dummyClass = dummyClass;
  }

  public String getSomething() {
    return dummyClass.getSomething();
  }
}
