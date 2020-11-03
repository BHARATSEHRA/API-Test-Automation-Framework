package com.store.common.util;

import com.fasterxml.jackson.annotation.JsonFormat.Value;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.List;

/*Class Description - This class is for serialization and deserialization of json to data object and vice versa
 */

public class JsonMapper {
	private static ObjectMapper mapper;

	static {
		mapper = new ObjectMapper();
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

	}

	// Dto to json

	public static String toJson(Object dto) {
		try {
			return mapper.writeValueAsString(dto);
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed mapping from " + dto.getClass().getSimpleName() + " to JSON. "
					+ dto.getClass().getSimpleName() + ": " + dto.toString(), e);
		}
	}

	public static <T> T fromJson(String json, Class<T> valueType) {
		try {
			return mapper.readValue(json, valueType);
		} catch (IOException e) {
			throw new RuntimeException("Failed mapping from JSON to " + valueType.getSimpleName() + ". JSON: " + json,
					e);
		}
	}

	//Json array to List DTO
	public static <T> T fromJsonArray(String json, Class<T> valueType) {

		try {
			TypeFactory typeFactory = mapper.getTypeFactory();
			return mapper.readValue(json, typeFactory.constructCollectionType(List.class, valueType));

			// return mapper.readValue(json, valueType);
		} catch (IOException e) {
			throw new RuntimeException("Failed mapping from JSON to ", e);
		}
	}

}
