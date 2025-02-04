package com.cts.ppstores;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;
//import org.slf4j.*;

@SpringBootApplication
@Slf4j
public class PPStoresApplication {

	public static void main(String[] args) {
		SpringApplication.run(PPStoresApplication.class, args);
		log.info("PP Stores Application started successfully.");
	}
}
