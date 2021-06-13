package com.app.passtard;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PasstardApplication {
	public static void main(String[] args) {
		SpringApplication.run(PasstardApplication.class, args);
	}

}
