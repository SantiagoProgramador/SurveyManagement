package com.riwi.filtro.infrastructure.abstracts;

import org.apache.catalina.connector.Response;
import org.springframework.data.domain.Page;

public interface BaseService<Request, Response, Id> {

  public Page<Response> getAll(int size, int page);

  public Response getById(Id id);

  public Response create(Request request);

  public Response update(Id id, Request request);

  public void delete(Id id);
}
