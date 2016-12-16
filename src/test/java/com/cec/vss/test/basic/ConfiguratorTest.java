package com.cec.vss.test.basic;

import java.lang.reflect.Field;
import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;

import com.cec.vss.config.Configurator;

public class ConfiguratorTest {

	@Test
	public void testSetConfigPath() throws Exception{

		Configurator.setConfigPath("test");

		Field field = Configurator.class.getDeclaredField("configPath");

		field.setAccessible(true);

		Assert.assertTrue("test".equals(field.get(Configurator.class).toString()));

	}

	@Test
	public void testReadConfig(){

		Configurator.setConfigPath("./src/test/resources/config.properties");

		Configurator.readConfig();

		Assert.assertTrue(Configurator.isHealthy());

	}

	@Test
	public void testReadConfigBad(){

		Configurator.setConfigPath("./src/test/resources/config.properties.bad");

		Configurator.readConfig();

		Assert.assertFalse(Configurator.isHealthy());

	}

	@Test
	public void testGetAndSetGlobalConfig(){

		Properties props = new Properties();

		props.put("test", "value");

		Configurator.setGlobalConfig(props);

		Properties readProps = Configurator.getGlobalConfig();

		Assert.assertTrue(readProps.contains("value"));

	}

	@Test
	public void testGetThreadsafeConfig(){

		Properties props = new Properties();

		props.put("test", "value");

		Configurator.setGlobalConfig(props);

		Properties readProps = Configurator.getThreadsafeConfig();

		Assert.assertTrue(readProps.contains("value"));

	}

	@Test
	public void testIsHealthy() throws Exception{

		Configurator.setConfigPath("./src/test/resources/config.properties");

		Configurator.readConfig();

		Assert.assertTrue(Configurator.isHealthy());

	}

}
