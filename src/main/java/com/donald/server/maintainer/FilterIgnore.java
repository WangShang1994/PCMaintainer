package com.donald.server.maintainer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilterIgnore {

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	private static Map<String, String> urls = new ConcurrentHashMap<>();

	static {
		urls.put("/loginPage", "/loginPage");
		urls.put("/sendLoginMail", "/sendLoginMail");
		urls.put("/loginSuccess", "/loginSuccess");
		logger.info("Ignore URL:{}", urls.keySet());
	}

	private FilterIgnore() {

	}

	public static boolean isIgnore(String url) {
		return urls.containsKey(url);
	}

}
