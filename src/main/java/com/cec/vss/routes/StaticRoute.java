package com.cec.vss.routes;

import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.StaticHandler;

public final class StaticRoute {

	private StaticRoute(){
		super();
	}

	public static final Route addStaticRoute(Router router){

		StaticHandler staticHandler = StaticHandler.create();
		staticHandler.setIndexPage("index.html");
		staticHandler.setCachingEnabled(false);

		return router.route().handler(staticHandler);

	}

}
