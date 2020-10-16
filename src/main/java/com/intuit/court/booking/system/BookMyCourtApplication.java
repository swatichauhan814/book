package com.intuit.court.booking.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class BookMyCourtApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookMyCourtApplication.class, args);
	}

}