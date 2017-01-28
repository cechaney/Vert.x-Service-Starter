package com.cec.vss.routes;

import java.util.Properties;

import com.cec.vss.config.ConfigConstants;
import com.cec.vss.config.Configurator;
import com.cec.vss.handlers.BasicEndHandler;
import com.cec.vss.handlers.BasicWebErrorHandler;
import com.cec.vss.handlers.GenericWebHandler;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public final class HelloRoute {

  private static final String ROUTE_PATH = "/hello";

  private HelloRoute() {
    super();
  }

  public static final Route addInterestRoute(Router router, HttpClient httpClient) {

    return router.route(ROUTE_PATH).handler(new HelloHandler(httpClient));

  }

}

class HelloHandler extends GenericWebHandler {

  private HttpClient httpClient;

  public HelloHandler(HttpClient httpClient) {
    this.httpClient = httpClient;
  }

  @Override
  public void handle(RoutingContext context) {

    super.handle(context);

    HttpClientRequest serviceRequest = httpClient.get(8080, "localhost", "/hello.html", clientResp -> {

      Properties config = Configurator.getThreadsafeConfig();
      response.putHeader("content-type", clientResp.getHeader("content-type"));

      clientResp.endHandler(new BasicEndHandler(response));

      response.write(config.getProperty(ConfigConstants.TEST_PROP) + " ");

      Pump.pump(clientResp, response).start();

    });

    serviceRequest.exceptionHandler(new BasicWebErrorHandler(response));

    serviceRequest.end();

  }
}