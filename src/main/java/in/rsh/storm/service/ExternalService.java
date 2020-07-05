package in.rsh.storm.service;

import com.google.inject.Inject;
import in.rsh.storm.client.HttpClient;

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
