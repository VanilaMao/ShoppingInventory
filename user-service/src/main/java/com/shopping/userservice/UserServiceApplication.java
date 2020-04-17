package com.shopping.userservice;

import com.shopping.userservice.domains.AuthenticationUser;
import com.shopping.userservice.services.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.UUID;

@SpringBootApplication
public class UserServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Autowired
	@Bean
	public ApplicationRunner runner(ProducerService service) {
		return args -> {
			service.sendMessage(AuthenticationUser.builder().name("123").password("345").id(UUID.randomUUID()).build());
		};
	}
}
