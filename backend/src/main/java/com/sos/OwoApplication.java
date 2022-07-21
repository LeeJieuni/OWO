package com.sos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@SpringBootApplication
public class OwoApplication {

	public static void main(String[] args) {
		SpringApplication.run(OwoApplication.class, args);

	}

}
