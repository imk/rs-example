package de.wdrmg.digital.example.rs.impl;

import org.apache.cxf.interceptor.security.AccessDeniedException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class AccessDeniedMapper implements ExceptionMapper<AccessDeniedException> {

  @Override
  public Response toResponse(AccessDeniedException exception) {
    return Response.status(Response.Status.FORBIDDEN).entity(exception.getMessage()).build();
  }

}
