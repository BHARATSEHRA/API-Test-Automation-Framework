package com.store.restClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.store.common.util.Utilities;
import com.store.customException.CustomExceptions;
import com.store.reporterLogger.ReportLogger;

import org.json.JSONArray;
import org.json.JSONObject;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/*
 * Class Description - This class is for wrapper methods for Rest assured methods -Post , get  , put , patch.
 * Test will use common methods with different parameter values
 */
public class RestClientWrapper {

	public static String resource;
	public static String baseUrl;
	private static RequestSpecification request;
	public static RestServerResponse serverResponse;
	private static Response restResponse;
	List<String> jsonList = new ArrayList<>();

	public RestClientWrapper(String baseUrl, RequestSpecification request) {
		RestClientWrapper.baseUrl = baseUrl;
		RestClientWrapper.request = request;

	}

	public RestServerResponse getAll(String resource) throws Exception {

		restResponse = request.when().get(baseUrl + "/" + resource);
		ReportLogger.info("Rest request end point: " + baseUrl + "/" + resource);
		serverResponse = new RestServerResponse(restResponse.getStatusCode(), restResponse.asString(),
				restResponse.getHeaders().toString());

		return serverResponse;
	}

	public RestServerResponse post(String resource, String bodyString) {
		request.headers("Content-Type", "application/json");
		request.body(bodyString);
		ReportLogger.info("Rest request end point: " + baseUrl + "/" + resource);
		ReportLogger.info("Request body is " + bodyString);
		restResponse = request.post(baseUrl + "/" + resource);
		serverResponse = new RestServerResponse(restResponse.getStatusCode(), restResponse.body().asString(),
				restResponse.getHeaders().toString());
		return serverResponse;

	}
	
	public RestServerResponse put(String resource, List<RestClientHeader> restClientheaders,
			Map<String, String> dataMap) {
		for (RestClientHeader header : restClientheaders) {
			request.headers(header.name, header.value);
		}
		JSONObject jsonPatched = new JSONObject();
		for (HashMap.Entry<String, String> put : dataMap.entrySet()) {
			jsonPatched.put(put.getKey(), put.getValue());
		}
		ReportLogger.info("Patched body is " + jsonPatched.toString());
		request.body(jsonPatched.toString());
		ReportLogger.info("Rest request end point: " + baseUrl + "/" + resource );
		restResponse = request.put(baseUrl + "/" + resource);
		serverResponse = new RestServerResponse(restResponse.getStatusCode(), restResponse.body().asString(),
				restResponse.getHeaders().toString());
		return serverResponse;
	}
	
	/*
	 * Purpose of Method-  This method is used to for the Request Type as GET.
	 * In this method we are fetching/retrieving individual record from the server
	 * Request Type - GET
	 */
	public RestServerResponse get(String resource, List<RestClientHeader> restClientheaders, String uniqueNumber) 
			throws Exception {

		for (RestClientHeader header : restClientheaders) {
			request.headers(header.name, header.value);
		}
		restResponse = request.get(baseUrl + "/" + resource + "/" + uniqueNumber + "");

		JSONObject JSONResponseBody = new JSONObject(restResponse.body().asString());
		serverResponse = new RestServerResponse(restResponse.getStatusCode(), JSONResponseBody.toString(),
				restResponse.getHeaders().toString());
		return serverResponse;
	}
	
	public RestServerResponse delete(String resource, List<RestClientHeader> restClientheaders, String uniqueNumber) {

		for (RestClientHeader header : restClientheaders) {
			System.out.println(header.name);
			request.headers(header.name, header.value);
		}
		restResponse = request.delete(baseUrl + "/" + resource + "/" + uniqueNumber + "");
		System.out.println(baseUrl + "/" + resource + "/" + uniqueNumber + "");
		serverResponse = new RestServerResponse(restResponse.getStatusCode(), restResponse.body().asString(),
				restResponse.getHeaders().toString());
		return serverResponse;

	}
	
}
