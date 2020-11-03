package com.store.restClient;

public class RestServerResponse {

	private int code;
	private String headers;
	private String body;

	public RestServerResponse(final int code, final String body, final String headers) {
		this.code = code;
		this.body = body;
		this.headers = headers;
	}

	public int getCode() {
		return code;
	}

	public String getBody() {
		return body;
	}

	public String getHeaders() {
		return headers;
	}

}
