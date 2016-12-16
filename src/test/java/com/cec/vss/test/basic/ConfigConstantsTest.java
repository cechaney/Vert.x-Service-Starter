package com.cec.vss.test.basic;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.cec.vss.config.ConfigConstants;

public class ConfigConstantsTest {

	@Test
	public void testConstructor() throws Exception{

		Constructor<ConfigConstants> constructor;

		constructor = ConfigConstants.class.getDeclaredConstructor();

		constructor.setAccessible(true);

		ConfigConstants instance = constructor.newInstance();

		Assert.assertTrue(instance != null);

	}

	@Test
	public void testRequiredFields(){

		ArrayList<String> requiredFields = new ArrayList<>(Arrays.asList(
				"SERVICE_PORT",
				"CONFIG_POLL_INTERVAL",
				"ACCESS_TOKEN",
				"API_HOST",
				"API_PORT",
				"API_PATH",
				"API_AUTH_PARAM"
		));

		Field fields[] = ConfigConstants.class.getDeclaredFields();

		for(Field field : fields){

			if(field.getName() != "$jacocoData"){

				if(!requiredFields.contains(field.getName())){
					Assert.fail(field.getName() + " does not exist in class");
				}

			}

		}

	}

}
