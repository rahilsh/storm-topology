package in.rsh.sst.latest.service;

import com.google.inject.Inject;
import in.rsh.sst.latest.client.DummyClass;

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
