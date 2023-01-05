package com.bonheur;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BonheurServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BonheurServerApplication.class, args);
	}

}
