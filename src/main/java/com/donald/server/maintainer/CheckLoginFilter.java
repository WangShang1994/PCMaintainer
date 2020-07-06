/**
 * 
 */
package com.donald.server.maintainer;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebFilter(urlPatterns = "*", filterName = "checkLoginFilter")
public class CheckLoginFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger("SYSTEM");

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (FilterIgnore.isIgnore(req.getRequestURI())) {
			logger.info("Ignore Login Check. Path: {}", req.getRequestURI());
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		logger.info("URL: {} SessionId: {}", req.getRequestURI(), sessionId);
		if ("/login".equals(req.getRequestURI())) {
			logger.info("Do Login. SessionId: {}", sessionId);
			String token = req.getParameter(Const.SESSION_TOKEN);
			if (TokenManager.isValidToken(token)) {
				logger.info("Verify Token Pass! SessionId: {}", sessionId);
				session.setAttribute(Const.SESSION_TOKEN, token);
				TokenManager.login(sessionId, token);
				((HttpServletResponse) response).sendRedirect("/index");
				return;
			} else {
				logger.info("Verify Token Fail! SessionId: {}", sessionId);
				((HttpServletResponse) response).sendRedirect("/loginPage");
				return;
			}
		}
		Object token = session.getAttribute(Const.SESSION_TOKEN);
		if ((token != null) && (TokenManager.checkToken(sessionId, token.toString()))) {
			logger.info("Check Token Pass! SessionId: {}", sessionId);
			chain.doFilter(request, response);
		} else {
			logger.info("Check Token Fail! SessionId: {}", sessionId);
			((HttpServletResponse) response).sendRedirect("/loginPage");
		}

	}

}
