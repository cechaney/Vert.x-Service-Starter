package com.cec.vss.routes;

import java.util.Properties;

import com.cec.vss.config.ConfigConstants;
import com.cec.vss.config.Configurator;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.streams.Pump;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public final class HelloRoute{

	//private static final Logger LOGGER = Logger.getLogger(HelloRoute.class.getName());

	private static final String ROUTE_PATH = "/hello";

	private HelloRoute(){
		super();
	}

	public static final Route addInterestRoute(Router router, HttpClient httpClient){

		return router.route(ROUTE_PATH).handler(routingContext -> {

			Properties config = Configurator.getThreadsafeConfig();

			HttpServerResponse response = routingContext.response();

			response.setChunked(true);

			HttpClientRequest serviceRequest = httpClient.get(8080, "localhost", "/hello.html", clientResp -> {

				response.putHeader("content-type", clientResp.getHeader("content-type"));

				clientResp.endHandler(endHandler ->{
					response.end();
				});

				response.write(config.getProperty(ConfigConstants.TEST_PROP) + " ");

				Pump.pump(clientResp, response).start();

			});

			serviceRequest.exceptionHandler(e -> {
				System.out.println("Received exception: " + e.getMessage());
				e.printStackTrace();
				response.end();
			});

			serviceRequest.end();

		});

	}

}
