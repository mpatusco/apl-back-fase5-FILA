package com.fiap.fila;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FilaApplication {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(FilaApplication.class);
//		app.addListeners(new ListenerOfSecrets());
		app.run(args);
	}
}