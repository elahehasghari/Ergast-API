package com.easghari.ergast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ErgastApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErgastApplication.class, args);
	}

}
