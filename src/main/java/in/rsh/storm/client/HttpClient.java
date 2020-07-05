package in.rsh.storm.client;

public class HttpClient {

  private final String something;

  public HttpClient(String something) {
    this.something = something;
  }

  public String getSomething() {
    return something;
  }
}
