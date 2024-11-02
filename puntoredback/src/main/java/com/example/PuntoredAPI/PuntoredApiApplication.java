package com.example.PuntoredAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.PuntoredAPI.model")
public class PuntoredApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PuntoredApiApplication.class, args);
	}

}
