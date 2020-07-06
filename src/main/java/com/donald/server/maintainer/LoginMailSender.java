/**
 * 
 */
package com.donald.server.maintainer;

import java.util.Map;
import java.util.Properties;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 * @author donald
 *
 */
public class LoginMailSender {

	private String host;

	private String userName;

	private String password;

	private String serverHost;

	private String targetAddress;

	private String protocol;

	private String port;

	private static final String LINK_TEMPLEATE = "<a href=\"%s\">Please click the link to login!</a>";

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	public LoginMailSender(Map<String, String> paraMap) {
		this.host = paraMap.get("spring.mail.host");
		this.userName = paraMap.get("spring.mail.username");
		this.password = paraMap.get("spring.mail.password");
		this.serverHost = paraMap.get("spring.server.host");
		this.targetAddress = paraMap.get("spring.mail.target.mail");
		this.protocol = paraMap.get("spring.mail.protocol");
		this.port = paraMap.get("spring.mail.port");
	}

	public void send() throws Exception {
		JavaMailSenderImpl sender = new JavaMailSenderImpl();
		sender.setHost(host);
		sender.setUsername(this.userName);
		sender.setPassword(password);
		sender.setProtocol(this.protocol);
		sender.setPort(Integer.parseInt(this.port));
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		sender.setJavaMailProperties(prop);
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(this.userName);
		helper.setTo(this.targetAddress);
		helper.setText(getMessageContent(), true);
		logger.info("Mail Content: {}", message.getContent());
		sender.send(message);
	}

	private String getMessageContent() {
		StringBuilder sb = new StringBuilder();
		sb.append("<html><body>"
				+ String.format(LINK_TEMPLEATE, serverHost + "?" + Const.SESSION_TOKEN + "=" + TokenManager.getToken())
				+ "</body></html>");
		return sb.toString();
	}

}
