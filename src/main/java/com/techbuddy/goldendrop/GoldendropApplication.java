package com.techbuddy.goldendrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableJpaAuditing
@Configuration
@EnableWebMvc
@EnableAsync
public class GoldendropApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldendropApplication.class, args);
	}

}
