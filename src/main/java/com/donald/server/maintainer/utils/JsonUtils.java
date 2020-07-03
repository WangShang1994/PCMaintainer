/**
 * 
 */
package com.donald.server.maintainer.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author donald
 *
 */
public class JsonUtils {

	private JsonUtils() {

	}

	public static String getJsonString(Object o) throws JsonProcessingException {

		return new ObjectMapper().writeValueAsString(o);
	}

}
