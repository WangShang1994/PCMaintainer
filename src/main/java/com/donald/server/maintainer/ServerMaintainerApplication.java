package com.donald.server.maintainer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = {"com.donald.server.maintainer"})
public class ServerMaintainerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServerMaintainerApplication.class, args);
	}

}
