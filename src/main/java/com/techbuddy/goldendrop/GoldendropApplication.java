package com.techbuddy.goldendrop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class GoldendropApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoldendropApplication.class, args);
	}

}
