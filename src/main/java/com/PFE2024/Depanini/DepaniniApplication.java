package com.PFE2024.Depanini;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.PFE2024.Depanini.model")
public class DepaniniApplication {

	public static void main(String[] args) {
		SpringApplication.run(DepaniniApplication.class, args);
		System.out.println("heeeloo here we start ");
	}

}
