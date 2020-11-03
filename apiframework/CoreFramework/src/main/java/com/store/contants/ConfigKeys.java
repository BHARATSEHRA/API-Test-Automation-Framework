package com.store.contants;

/**
 * All configuration keys and their configuration mapping are added here.They
 * must have a correct configuration property set.
 * 
 *
 */
public enum ConfigKeys {

	ACCESSTOKEN("access_Token"), BASEURL("baseurl"), CLIENTID("client_ID"),CURRENT_USER_DIRECTORY("currentUserDir"), TEST_DATA_EXCEL("api_excel");

	private String key;

	ConfigKeys(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public String getName() {
		return this.name();
	}
}
