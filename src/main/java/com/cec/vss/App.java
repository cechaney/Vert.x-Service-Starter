package com.cec.vss;

import java.util.Calendar;
import java.util.HashMap;
import java.util.logging.Logger;

import com.cec.vss.config.ConfigConstants;
import com.cec.vss.config.Configurator;
import com.cec.vss.routes.HelloRoute;
import com.cec.vss.routes.StaticRoute;

import io.vertx.core.Vertx;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientOptions;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpVersion;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;

public class App {

	private static final Logger LOGGER = Logger.getLogger(App.class.getName());

	private static Vertx vertx;
	private static HttpServer server;
	private static Router router;
	private static HttpClient httpClient;
	private static HashMap<String, Route> routes;

	public static void main(String args[]) {

		LOGGER.info("Starting up...");
		Long startTime = Calendar.getInstance().getTimeInMillis();

		initVertxCore();
		initConfigSys();
		initHttpClientSys();
		initHttpServerSys();

		Long endTime = Calendar.getInstance().getTimeInMillis();
		LOGGER.info("Startup complete: " + (endTime - startTime) + "ms");

	}

	private static final  void initVertxCore(){

		//See http://vertx.io/docs/apidocs/io/vertx/core/VertxOptions.html for Vert.x's default values
		vertx = Vertx.vertx();

	}

	private static final void initConfigSys(){

		String configPath = System.getProperties().getProperty("configPath");

		if(configPath == null){
			LOGGER.severe("No configPath specfied in system properties.  Did you start the app with a -DconfigPath setting?");
			System.exit(1);
		} else {

			Configurator.setConfigPath(configPath);
			Configurator.readConfig();

			if(Configurator.isHealthy()){

				Integer pollInterval = Integer.valueOf(
						Configurator.getGlobalConfig().get(ConfigConstants.CONFIG_POLL_INTERVAL).toString());

				vertx.setPeriodic(pollInterval, handler ->{
					Configurator.readConfig();
				});

			} else {
				LOGGER.severe("Configuration failed.  Was the file specified with configPath correct?");
				System.exit(1);
			}

		}

	}

	private static final void initHttpClientSys(){

		HttpClientOptions options = new HttpClientOptions()
				.setProtocolVersion(HttpVersion.HTTP_1_1)
				.setSsl(false)
				.setTrustAll(false);

		httpClient = vertx.createHttpClient(options);

	}

	private static final void initHttpServerSys(){

		server = vertx.createHttpServer();

		router = Router.router(vertx);

		addRoutes();

		server.requestHandler(router::accept);

		Integer port = Integer.valueOf(Configurator.getGlobalConfig().get(ConfigConstants.SERVICE_PORT).toString());

		server.listen(port);
	}

	private static final void addRoutes(){

		routes = new HashMap<>();

		routes.put("helloRoute", HelloRoute.addInterestRoute(router, httpClient));

		//Static route should always be last
		routes.put("staticRoute", StaticRoute.addStaticRoute(router));

	}

}
