package com.store.reader;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Properties;

import com.store.contants.ConfigKeys;
import com.store.reporterLogger.ReportLogger;

/*This class is for reading property file for environment configuration
 * For each environment properties file needs to be created and pass as -Dconfig.file=
 * Else will pick from localhost.properties file
 * 
 */

public class Config {

	public static String ACCESSTOKEN;
	public static String BASEURL;
	public static String CLIENTID;
	private static String CONFIG_FILE = "config.file";
	// Default config file
	private static final String DEFAULT_CONFIG_FILE = "localhost.properties";
	private static boolean hasAlreadyReadProperties = false;
	private static Properties executionSettings = null;
	public static String CLIENTPORT;
	public static String CURRENT_USER_DIRECTORY;
	public static String TEST_DATA_EXCEL;

	public static void readConfig() throws Exception {
		if (!hasAlreadyReadProperties) {
			String propertiesFile = System.getProperty("config.file", CONFIG_FILE);
			if (propertiesFile.equals(null)) {
				throw new Exception("No properties file to be read, '" + propertiesFile + "'.");
			}
			InputStream inputStream = Config.class.getClassLoader().getResourceAsStream(DEFAULT_CONFIG_FILE);
			executionSettings = new Properties();
			try {
				executionSettings.load(inputStream);
			} catch (IOException ioe) {
				throw new Exception("Problems parsing properties file, '" + propertiesFile + "'.", ioe);
			}
			ConfigKeys[] configKeys = ConfigKeys.values();
			for (ConfigKeys configKey : configKeys) {
				String value = getConfigProperty(configKey.getKey());
				if (value != null) {
					Field f = Config.class.getField(configKey.getName());
					f.setAccessible(true);
					f.set(Config.class, value.trim());
				}
			}
			hasAlreadyReadProperties = true;

			configKeys = ConfigKeys.values();
			for (ConfigKeys configKey : configKeys) {
				Field configField = Config.class.getField(configKey.getName());
				configField.setAccessible(true);
//				ReportLogger.info(configKey.getKey() + ": " + configField.get(Config.class));
			}

		}
	}

	private static String getConfigProperty(String propertyName) throws Exception {
		return executionSettings.containsKey(propertyName) ? executionSettings.getProperty(propertyName) : null;

	}

}
