package com.example.microservico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MicroservicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroservicoApplication.class, args);
	}

}
