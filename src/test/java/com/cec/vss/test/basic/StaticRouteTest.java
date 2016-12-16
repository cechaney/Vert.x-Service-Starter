package com.cec.vss.test.basic;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

import com.cec.vss.routes.StaticRoute;

public class StaticRouteTest {

	@Test
	public void testConstructor() throws Exception{

		Constructor<StaticRoute> constructor;

		constructor = StaticRoute.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		StaticRoute instance = constructor.newInstance();

		Assert.assertTrue(instance != null);

	}

}
