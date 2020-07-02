package com.donald.server.maintainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FilterIgnore {

	private static Map<String, String> urls = new ConcurrentHashMap<>();

	static {
		urls.put("/loginPage", "/loginPage");
		urls.put("/sendLoginMail", "/sendLoginMail");
		urls.put("/loginSuccess", "/loginSuccess");
	}

	private FilterIgnore() {

	}

	public static boolean isIgnore(String url) {
		return urls.containsKey(url);
	}

}
