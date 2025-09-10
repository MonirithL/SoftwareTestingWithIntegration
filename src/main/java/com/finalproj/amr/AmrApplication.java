package com.finalproj.amr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AmrApplication {

	public static void main(String[] args) {
		SpringApplication.run(AmrApplication.class, args);
	}

}
