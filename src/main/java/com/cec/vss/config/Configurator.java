package com.cec.vss.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.SerializationUtils;

public enum Configurator {

	INSTANCE;

	private static final Logger LOGGER = Logger.getLogger(Configurator.class.getName());

	private static final AtomicReference<String> configPath = new AtomicReference<>(null);
	private static final AtomicReference<Properties> config = new AtomicReference<>(new Properties());
	private static final AtomicReference<Boolean> health = new AtomicReference<>(false);

	public static final void setConfigPath(String path){
		configPath.set(path);
	}

	public static final void readConfig() {

		try (FileInputStream fis = new FileInputStream(configPath.get())) {

			Properties newConfig = new Properties();

			newConfig.load(fis);

			if (!newConfig.equals(getGlobalConfig())) {

				LOGGER.fine("Config change detected");

				setGlobalConfig(newConfig);

				health.set(true);

				LOGGER.fine("Config updated!");

			}

		} catch (IOException ioe) {

			LOGGER.log(Level.FINE, "Config file failed to load", ioe);

			setGlobalConfig(new Properties());

			health.set(false);

		}

	}

	public static final Properties getGlobalConfig(){
		return config.get();
	}

	public static final void setGlobalConfig(Properties props){
		config.set(props);
	}

	public static final Properties getThreadsafeConfig(){

        Properties props = SerializationUtils.clone(config.get());

        return props;

	}

	public static final Boolean isHealthy(){

		if(health.get()){
			return true;
		} else {
			return false;
		}

	}

}
