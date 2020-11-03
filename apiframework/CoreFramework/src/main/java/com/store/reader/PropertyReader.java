package com.store.reader;

import java.io.*;
import java.util.*;

/* Class Description - Class contains all the functions and variables related to Property.config   
*/

public class PropertyReader {

	private static Properties prop;

	public static HashMap<String, String> propertyMap;

	/*
	 * Function Description - Function will read and load the property file provided
	 * in parameter
	 */

	private void loadProps(String propertyFile) throws FileNotFoundException, IOException {

		File cfgfile = new File(propertyFile);
		if (cfgfile.exists()) {
			FileInputStream propin = new FileInputStream(cfgfile);
			prop.load(propin);
		}
	}

	/*
	 * Function Description - Function will return value of the key provided in
	 * function parameters
	 */

	public String readProperty(String propkey) {
		return prop.getProperty(propkey);
	}

	/*
	 * Function Description - Function will call function to read property file if
	 * Property Map is null and return the property map [Hashmap]
	 */

	public HashMap<String, String> getPropertyMap(String propertyPath) throws FileNotFoundException, IOException {
		if (propertyMap == null) {
			propertyMap = readProperties(propertyPath);
		}
		return propertyMap;
	}

	/*
	 * Function Description - Function will read properties and save it in a
	 * HashMap<String, String> called Property Map
	 */
	public HashMap<String, String> readProperties(String propertyPath) throws FileNotFoundException, IOException {
		prop = new Properties();
		HashMap<String, String> map = new HashMap<String, String>();
		String curDir = System.getProperty("user.dir");
		loadProps(curDir + propertyPath);
		Set<Object> keys = prop.keySet();
		for (Object k : keys) {
			String key = (String) k;
			map.put(key, (String) prop.getProperty(key));
		}
		return map;
	}

	public static HashMap<String, String> getPropertyMap() {
		return propertyMap;
	}

	public static void setPropertyMap(HashMap<String, String> propertyMap) {
		PropertyReader.propertyMap = propertyMap;
	}

}
