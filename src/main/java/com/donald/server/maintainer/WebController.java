package com.donald.server.maintainer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	private static final Logger logger = LoggerFactory.getLogger("WebController");

	@GetMapping(value = "/index")
	public String index() {
		return "index";
	}

	@GetMapping(value = "/loginPage")
	public String loginPage() {
		return "loginPage";
	}

	@GetMapping(value = "/sendLoginMail")
	public String sendLoginMail(@Value("${spring.mail.host}") String host,
			@Value("${spring.mail.username}") String username, @Value("${spring.mail.password}") String password,
			@Value("${spring.server.host}") String serverHost,
			@Value("${spring.mail.target.mail}") String targetAddress, @Value("${spring.mail.port}") String port,
			@Value("${spring.mail.protocol}") String protocol) {
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("spring.mail.host", host);
		paraMap.put("spring.mail.username", username);
		paraMap.put("spring.mail.password", password);
		paraMap.put("spring.server.host", serverHost);
		paraMap.put("spring.mail.target.mail", targetAddress);
		paraMap.put("spring.mail.port", String.valueOf(port));
		paraMap.put("spring.mail.protocol", protocol);
		LoginMailSender sender = new LoginMailSender(paraMap);
		logger.info("Request to send login mail.");
		try {
			sender.send();
		} catch (Exception e) {
			logger.error("Execute send login mail error!", e);
			return "loginPage";
		}

		return "loginSuccess";
	}

}
