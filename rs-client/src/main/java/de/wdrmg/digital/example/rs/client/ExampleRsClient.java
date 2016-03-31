package de.wdrmg.digital.example.rs.client;

import de.wdrmg.digital.example.rs.api.ExampleResource;
import de.wdrmg.digital.examples.rs.Example;

import org.apache.cxf.jaxrs.client.Client;
import org.apache.cxf.jaxrs.client.ClientConfiguration;
import org.apache.cxf.jaxrs.client.JAXRSClientFactory;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

public class ExampleRsClient {
  private String baseUrl;
  private long timeout;
  private String proxyHost;
  private int proxyPort;
  private final OAuthClientRequestFilter authFilter;

  public ExampleRsClient(final String baseUrl) {
    this(baseUrl, 0, null, 0, null, null, null, null);
  }

  public ExampleRsClient(final String baseUrl, final String oauthServerUrl, final String oauthKey,
      final String oauthSecret, final String callbackURL) {
    this(baseUrl, 0, null, 0, oauthServerUrl, oauthKey, oauthSecret, callbackURL);
  }

  public ExampleRsClient(final String baseUrl, final long timeout, final String oauthServerUrl, final String oauthKey,
      final String oauthSecret, final String callbackURL) {
    this(baseUrl, timeout, null, 0, oauthServerUrl, oauthKey, oauthSecret, callbackURL);
  }

  public ExampleRsClient(final String baseUrl, final String proxyHost, final int proxyPort, final String oauthServerUrl,
      final String oauthKey, final String oauthSecret, final String callbackURL) {
    this(baseUrl, 0, proxyHost, proxyPort, oauthServerUrl, oauthKey, oauthSecret, callbackURL);
  }

  public ExampleRsClient(final String baseUrl, final long timeout, final String proxyHost, final int proxyPort,
      final String oauthServerUrl, final String oauthKey, final String oauthSecret, final String callbackURL) {
    setBaseUrl(baseUrl);
    setTimeout(timeout);
    setProxyHost(proxyHost);
    setProxyPort(proxyPort);
    if (oauthServerUrl != null && oauthKey != null && oauthSecret != null && callbackURL != null) {
      authFilter = new OAuthClientRequestFilter(oauthServerUrl, oauthKey, oauthSecret, callbackURL);
    } else {
      authFilter = null;
    }
  }

  public String getBaseUrl() {
    return baseUrl;
  }

  public void setBaseUrl(final String baseUrl) {
    this.baseUrl = baseUrl;
  }

  public String getProxyHost() {
    return proxyHost;
  }

  public void setProxyHost(final String proxyHost) {
    this.proxyHost = proxyHost;
  }

  public int getProxyPort() {
    return proxyPort;
  }

  public void setProxyPort(final int proxyPort) {
    this.proxyPort = proxyPort;
  }

  public long getTimeout() {
    return timeout;
  }

  public void setTimeout(final long timeout) {
    this.timeout = timeout;
  }

  public ExampleResource getExampleClient() {
    return createClient(ExampleResource.class);
  }

  private <T> T createClient(final Class<T> klazz) {
    final List<Object> providers = new ArrayList<>();
    if (authFilter != null) {
      providers.add(authFilter);
    }
    final T proxy = JAXRSClientFactory.create(baseUrl, klazz, providers);
    final Client client = WebClient.client(proxy);
    client.accept(MediaType.APPLICATION_XML_TYPE).header("Cache-Control", "maxAge=60").header("Pragma", "maxAge=60");
    final boolean useProxy = proxyHost != null && proxyPort > 0;
    final boolean setTimeout = timeout > 0;
    if (setTimeout || useProxy) {
      final ClientConfiguration clientConfig = WebClient.getConfig(proxy);
      final HTTPClientPolicy httpClientConfig = clientConfig.getHttpConduit().getClient();
      httpClientConfig.setCacheControl("maxAge=120");
      if (setTimeout) {
        clientConfig.setSynchronousTimeout(timeout);
        httpClientConfig.setReceiveTimeout(timeout);
        httpClientConfig.setConnectionTimeout(timeout);
      }
      if (useProxy) {
        httpClientConfig.setProxyServer(proxyHost);
        httpClientConfig.setProxyServerPort(proxyPort);
      }
    }
    return proxy;
  }

  public static void main(final String[] args) {
    final ExampleRsClient client = new ExampleRsClient("http://localhost:8080/rs-server/");
    final ExampleResource exampleClient = client.getExampleClient();
    // Typ durch API vorgegeben
    System.out.println(exampleClient.index().getElements());
    System.out.println(exampleClient.get("a"));
    // Typ muss im Client explizit gesetzt werden
    System.out.println(exampleClient.indexResponse().readEntity(new GenericType<List<Example>>() {}));
    System.out.println(exampleClient.getResponse("a").readEntity(Example.class));
    final ExampleRsClient clientSecure =
        new ExampleRsClient("http://localhost:8080/rs-server/secure/", "http://localhost:8080/OAuthServer/oauth",
            "aiv6-eiqu-aiGh-8wie", "Hu6quioToongiet", "http://localhost:8080/rs-example/");
    final ExampleResource exampleClientSecure = clientSecure.getExampleClient();
    // Typ durch API vorgegeben
    System.out.println(exampleClientSecure.index().getElements());
    System.out.println(exampleClientSecure.get("a"));
    // Typ muss im Client explizit gesetzt werden
    System.out.println(exampleClientSecure.indexResponse().readEntity(new GenericType<List<Example>>() {}));
    System.out.println(exampleClientSecure.getResponse("a").readEntity(Example.class));
    // Generic Entity Type gesetzt, funktioniert problemlos im Marshalling, führt default zur Exception bei
    // Unmarshalling
    // System.out.println(exampleClient.indexGeneric().getEntity());
  }
}
