package de.wdrmg.digital.example.rs.impl;

import de.wdrmg.digital.example.rs.api.ExampleResource;
import de.wdrmg.digital.examples.rs.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ExampleResourceController implements ExampleResource {
  private static final Map<String, Example> ELEMENTS =
      Arrays.asList(new Example("a", "a1", "egal1"), new Example("b", "b2", "egal2"), new Example("c", "c3", "egal3"))
          .stream().collect(Collectors.toMap(Example::getKey, Function.identity()));

  @Override
  public Example.Wrapper index() {
    return new Example.Wrapper(new ArrayList<>(ELEMENTS.values()));
  }

  @Override
  public Example get(final String key) {
    if (ELEMENTS.containsKey(key)) {
      return ELEMENTS.get(key);
    } else {
      throw new NotFoundException("kein beispiel mit key " + key + " gefunden.");
    }
  }

  @Override
  public Response indexResponse() {
    return Response.ok(new GenericEntity<List<Example>>(new ArrayList<>(ELEMENTS.values())) {}).build();
  }

  @Override
  public Response getResponse(final String key) {
    if (ELEMENTS.containsKey(key)) {
      return Response.ok(ELEMENTS.get(key)).build();
    } else {
      return Response.status(Status.NOT_FOUND).build();
    }
  }

  @Override
  public GenericEntity<List<Example>> indexGeneric() {
    return new GenericEntity<List<Example>>(new ArrayList<>(ELEMENTS.values())) {};
  }

}
