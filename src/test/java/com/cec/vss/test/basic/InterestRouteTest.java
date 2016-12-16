package com.cec.vss.test.basic;

import java.lang.reflect.Constructor;

import org.junit.Assert;
import org.junit.Test;

import com.cec.vss.routes.HelloRoute;

public class InterestRouteTest {


	@Test
	public void testConstructor() throws Exception{

		Constructor<HelloRoute> constructor;

		constructor = HelloRoute.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		HelloRoute instance = constructor.newInstance();

		Assert.assertTrue(instance != null);

	}

}
