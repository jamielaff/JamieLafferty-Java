package com.jamielafferty.RPS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point will be 9090 (changed in application.yml)
 * 
 * @author Jamie
 *
 */
@SpringBootApplication
public class RPSApplication {

	public static void main(String[] args) {
		SpringApplication.run(RPSApplication.class, args);
	}
	
}