package com.shopping.userservice;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Value(value="${cors.url}")
	private String corsUrl;

	// not working
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				if(corsUrl!=null && !corsUrl.isEmpty()){
					registry.addMapping("/**")
							.allowedMethods("*")
							.allowedOrigins("*")
							.allowedHeaders("*")
							.allowCredentials(true);
				}
			}
		};
	}
}
