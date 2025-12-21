package br.hackathon.com;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class ApiFornecedorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiFornecedorApplication.class, args);
	}

}
