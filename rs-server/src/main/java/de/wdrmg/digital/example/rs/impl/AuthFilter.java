package de.wdrmg.digital.example.rs.impl;

import org.apache.cxf.interceptor.security.AccessDeniedException;
import org.apache.cxf.jaxrs.ext.MessageContext;
import org.apache.cxf.rs.security.oauth.data.OAuthContext;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;

public class AuthFilter implements ContainerRequestFilter {
  private static final String ROLE = "RSExample_Read";

  @Context
  private MessageContext jaxrsContext;

  @Override
  public void filter(ContainerRequestContext requestContext) throws IOException {
    if (!isUserInRole(ROLE)) {
      throw new AccessDeniedException("Access Denied");
    }
  }


  private boolean isUserInRole(String roleName) {
    if (jaxrsContext != null) {
      System.out.println("expected role: " + roleName);
      final OAuthContext oauth = jaxrsContext.getContent(OAuthContext.class);
      System.out.println(oauth);
      if (oauth != null && oauth.getSubject() != null && oauth.getSubject().getRoles() != null) {
        System.out.println(oauth.getSubject().getRoles());
        return oauth.getSubject().getRoles().contains(roleName);
      }
    }
    return false;
  }
}
