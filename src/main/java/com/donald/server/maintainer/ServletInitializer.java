package com.donald.server.maintainer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ServerMaintainerApplication.class);
	}

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		System.out.println("onStartup");
		super.onStartup(servletContext);
	}

}
