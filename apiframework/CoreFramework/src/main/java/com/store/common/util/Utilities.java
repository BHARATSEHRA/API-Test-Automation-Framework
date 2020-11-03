package com.store.common.util;

import io.restassured.path.json.JsonPath;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.Normalizer.Form;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import org.json.JSONObject;

/* Class Description - Class contains all the functions that are common for all the application and tests     
*/

public class Utilities {

	static StringWriter requestWriter, responseWriter, errorWriter;
	static PrintStream requestCapture, responseCapture, errorCapture;

	// Data providers excel data to map to form key value pair
	public static Map<String, String> excelDataToMap(List<String> columnName, List<String> columnValue) {

		Map<String, String> testData = new HashMap<String, String>();

		for (int i = 0; i < columnName.size(); i++) {
			testData.put(columnName.get(i), columnValue.get(i));
		}

		return testData;
	}

	

	public static String customAssertMessage(String validationMessage, String requestBody, String responseBody) {

		return "*Exception/Error*:"+validationMessage+"\n \n"+"\n *Request body*: "+requestBody+
				"*Response body*: "+responseBody;


	}

}
