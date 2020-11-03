package com.store.restClient;

//Class for query parameters
public class RestClientParameters {
	public final String parameterName;
	public final String parameterValue;

	public RestClientParameters(String parameterName, String parameterValue) {
		this.parameterName = parameterName;
		this.parameterValue = parameterValue;
	}
}
