package com.glwm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
public class GlTakeOutAdminApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GlTakeOutAdminApplication.class, args);
		System.out.println("项目启动成功");
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(GlTakeOutAdminApplication.class);
	}

}
