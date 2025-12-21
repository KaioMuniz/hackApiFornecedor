package br.hackathon.com.configurations;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

	@Bean
	public Queue queue() {
		return new Queue("cotacoes_encerradas",true);
	}
	
	@Bean
	public Queue newQueue() {
		return new Queue("convites_fornecedores",true);
	}
}
