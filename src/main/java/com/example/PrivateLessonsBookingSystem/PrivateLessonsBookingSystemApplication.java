package com.example.PrivateLessonsBookingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class PrivateLessonsBookingSystemApplication {
	public static void main(String[] args) {
        SpringApplication.run(PrivateLessonsBookingSystemApplication.class, args);
	}
}