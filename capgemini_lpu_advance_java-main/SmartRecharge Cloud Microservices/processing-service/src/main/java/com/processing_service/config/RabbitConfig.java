package com.processing_service.config;


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