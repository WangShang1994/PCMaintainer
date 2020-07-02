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

@WebFilter(urlPatterns = "*", filterName = "checkLoginFilter")
public class CheckLoginFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		if (FilterIgnore.isIgnore(req.getRequestURI())) {
			chain.doFilter(request, response);
			return;
		}
		HttpSession session = req.getSession();
		String sessionId = session.getId();
		System.out.println(req.getRequestURI() + "SessionId: " + sessionId);
		if ("/login".equals(req.getRequestURI())) {
			String token = req.getParameter(Const.SESSION_TOKEN);
			if (TokenManager.isValidToken(token)) {
				session.setAttribute(Const.SESSION_TOKEN, token);
				TokenManager.login(sessionId, token);
				((HttpServletResponse) response).sendRedirect("/index");
				return;
			} else {
				((HttpServletResponse) response).sendRedirect("/loginPage");
				return;
			}
		}
		Object token = session.getAttribute(Const.SESSION_TOKEN);
		if ((token != null) && (TokenManager.checkToken(sessionId, token.toString()))) {
			chain.doFilter(request, response);
		} else {
			((HttpServletResponse) response).sendRedirect("/loginPage");
		}

	}

}
