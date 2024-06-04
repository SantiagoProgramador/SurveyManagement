package com.riwi.filtro.infrastructure.abstracts;

import java.util.List;

import org.apache.catalina.connector.Response;

public interface BaseService<Request, Response, Id> {
  public List<Response> getAll();
  public Response getById(Id id);
  public Response create(Request request);
  public Response update(Id id, Request request);
  public void delete(Id id);
}
