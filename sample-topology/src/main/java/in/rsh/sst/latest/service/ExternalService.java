package in.rsh.sst.latest.service;

import com.google.inject.Inject;
import in.rsh.sst.latest.client.HttpClient;

public class ExternalService {

  private final HttpClient httpClient;

  @Inject
  public ExternalService(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  public String getSomething() {
    return httpClient.getSomething();
  }
}
