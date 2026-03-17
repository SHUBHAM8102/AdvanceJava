package com.processing_service.rabbitlistener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.processing_service.config.RabbitConfig;
import com.processing_service.rechargedto.RechargeDTO;
import com.processing_service.rechargeentity.RechargeRequest;

@Component 
public class DLQConsumer {

	@RabbitListener(queues = RabbitConfig.DLQ)
	public void readFailMessage(RechargeDTO dto) { 
		System.out.println("Failed message for: " + dto.getMobilenum());
	}
}