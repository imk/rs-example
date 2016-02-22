package de.wdrmg.digital.example.rs.api;

import de.wdrmg.digital.examples.rs.Example;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;

@Path("/example")
public interface ExampleResource {

  @GET
  @Path("/a")
  public Example.Wrapper index();

  @GET
  @Path("/a/{key}")
  public Example get(final @PathParam("key") String key);

  @GET
  @Path("/b")
  public Response indexResponse();

  @GET
  @Path("/b/{key}")
  public Response getResponse(final @PathParam("key") String key);

  @GET
  @Path("/c")
  public GenericEntity<List<Example>> indexGeneric();
}
