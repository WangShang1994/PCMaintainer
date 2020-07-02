package com.donald.server.maintainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.util.StringUtils;

public class TokenManager {

	protected static Map<String, String> tokens = new ConcurrentHashMap<>();

	protected static Map<String, Long> tokenExpireTime = new ConcurrentHashMap<>();

	private static ExecutorService threadPool = Executors.newSingleThreadExecutor();

	private static List<String> vaildToken = Collections.synchronizedList(new ArrayList<>());

	static {
		threadPool.execute(new RefreshTokenTimeThread());
	}

	private TokenManager() {
	}

	public static boolean isValidToken(String token) {
		return vaildToken.contains(token);
	}

	public static boolean checkToken(String sessionId, String token) {
		boolean isPass = false;
		try {
			isPass = (!StringUtils.isEmpty(tokens.get(sessionId))) && tokens.get(sessionId).equals(token);
			return isPass;
		} finally {
			if (isPass) {
				tokenExpireTime.put(sessionId, System.currentTimeMillis());
			}
		}
	}

	public static void login(String sessionId, String token) {
		tokens.put(sessionId, token);
		tokenExpireTime.put(sessionId, System.currentTimeMillis());
	}

	public static String getToken() {
		String token = UUID.randomUUID().toString().replace("-", "");
		if (!vaildToken.isEmpty()) {
			vaildToken.remove(0);
		}
		vaildToken.add(token);
		return token;
	}

}

class RefreshTokenTimeThread implements Runnable {

	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				Iterator<Entry<String, Long>> it = TokenManager.tokenExpireTime.entrySet().iterator();
				long now = System.currentTimeMillis();
				while (it.hasNext()) {
					Entry<String, Long> e = it.next();
					long pass = now - e.getValue();
					if (pass >= 300000) {
						TokenManager.tokens.remove(e.getKey());
						TokenManager.tokenExpireTime.remove(e.getKey());
						System.out.println("Session Timeout: SessionId: " + e.getKey());
					}
				}
				Thread.sleep(3000);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
