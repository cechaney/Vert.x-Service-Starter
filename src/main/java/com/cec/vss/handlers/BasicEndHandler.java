package com.cec.vss.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;

public class BasicEndHandler implements Handler<Void> {
  
  
  private final HttpServerResponse response;

  public BasicEndHandler(HttpServerResponse response) {
    this.response = response;
  }

  @Override
  public void handle(Void event) {
    response.end();
  }
}
