package com.cec.vss.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerResponse;

public class BasicWebErrorHandler implements Handler<Throwable> {
  
  private final HttpServerResponse response;

  public BasicWebErrorHandler(HttpServerResponse response) {
    this.response = response;
  }

  @Override
  public void handle(Throwable e) {
    System.out.println("Received exception: " + e.getMessage());
    e.printStackTrace();
    response.end();
  }

}
