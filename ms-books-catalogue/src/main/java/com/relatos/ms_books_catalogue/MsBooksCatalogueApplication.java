package com.relatos.ms_books_catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MsBooksCatalogueApplication {

	public static void main(String[] args) {
		System.setProperty("spring.application.name", "ms-books-catalogue");
		SpringApplication.run(MsBooksCatalogueApplication.class, args);
	}

}
