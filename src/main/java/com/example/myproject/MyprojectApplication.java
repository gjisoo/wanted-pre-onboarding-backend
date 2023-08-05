package com.example.myproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = "com.example.myproject.entity")
public class MyprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MyprojectApplication.class, args);
	}

}
