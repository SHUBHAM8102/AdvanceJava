package com.recharge_service.config;

import java.util.HashMap;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

	public static final String Queue_Name = "recharge.queue";
	public static final String DLQ = "recharge.dlq";

	@Bean
	public Queue rechargequeue() {
		HashMap<String, Object> args = new HashMap<>();
		args.put("x-message-ttl", 10000);
		args.put("x-dead-letter-exchange", "");
		args.put("x-dead-letter-routing-key", DLQ); 
		
		return new Queue(Queue_Name, true, false, false, args);
	}

	@Bean
	public Queue deadletter() {
		return new Queue(DLQ, true);
	}
	
	  @Bean
	    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionfactory) {
	    	RabbitTemplate rt = new RabbitTemplate(connectionfactory);
	    	rt.setObservationEnabled(true);
			return rt;
	    	
	    }
	    @Bean
	    public SimpleRabbitListenerContainerFactory rabbitlistenercontainerfactory(ConnectionFactory connectionfactory) {
	    	SimpleRabbitListenerContainerFactory srlcf = new SimpleRabbitListenerContainerFactory();
	    	srlcf.setConnectionFactory(connectionfactory);
	    	srlcf.setObservationEnabled(true);
	    	return srlcf;
	    	
	    }
}
