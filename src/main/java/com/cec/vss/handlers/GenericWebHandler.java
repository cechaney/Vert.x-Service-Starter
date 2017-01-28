package com.cec.vss.handlers;

import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;

public class GenericWebHandler implements Handler<RoutingContext> {

  protected HttpServerRequest request;
  protected HttpServerResponse response;

  @Override
  public void handle(RoutingContext context) {
    this.request = context.request();
    this.response = context.response();
    response.setChunked(true);
    
  }


}
