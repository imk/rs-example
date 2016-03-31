package de.wdrmg.digital.example.rs.client;

import de.wdr.oauth.clientmanagerutil.OAuthClientManager;

import org.apache.cxf.rs.security.oauth.client.OAuthClientUtils;
import org.apache.cxf.rs.security.oauth.provider.OAuthServiceException;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

@Provider
public class OAuthClientRequestFilter implements ClientRequestFilter {
  private final OAuthClientManager oAuthClientManager;
  private final String callbackURL;
  private OAuthClientUtils.Token accessToken = null;

  public OAuthClientRequestFilter(final String oauthServerUrl, final String oauthKey, final String oauthSecret,
      final String callbackURL) {
    this.callbackURL = callbackURL;
    oAuthClientManager = new OAuthClientManager(oauthServerUrl, oauthKey, oauthSecret);
  }

  @Override
  public void filter(final ClientRequestContext requestContext) throws IOException {
    final MultivaluedMap<String, Object> headers = requestContext.getHeaders();
    System.out.println(headers);
    headers.putSingle(HttpHeaders.AUTHORIZATION,
        generateOAuthHeader(requestContext.getUri().toString(), requestContext.getMethod()));
  }


  private String generateOAuthHeader(final String serviceURL, final String method) {
    String oauthString = null;
    int i = 0;
    while (oauthString == null && i < 3) {
      i++;
      try {
        if (accessToken == null) {
          refreshAccessToken();
        }
        oauthString = oAuthClientManager.createAuthorizationHeader(accessToken, method, serviceURL);
      } catch (final OAuthServiceException e) {
        accessToken = null;
      }
    }
    return oauthString;
  }

  private synchronized void refreshAccessToken() throws OAuthServiceException {
    final Map<String, String> map = new HashMap<>();
    map.put(org.apache.cxf.rs.security.oauth.utils.OAuthConstants.X_OAUTH_SCOPE, "RSExample");
    final OAuthClientUtils.Token requestToken = oAuthClientManager.getRequestToken(URI.create(callbackURL), map);
    accessToken = oAuthClientManager.getAccessToken(requestToken, null);
  }
}
