package de.wdrmg.digital.example.rs.impl;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class NotFoundMapper implements ExceptionMapper<NotFoundException> {

  @Override
  public Response toResponse(NotFoundException exception) {
    return Response.status(Response.Status.NOT_FOUND).entity(exception.getMessageEntity()).build();
  }

}
