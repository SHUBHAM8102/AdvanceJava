package com.recharge_service.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.recharge_service.config.RabbitConfig;
import com.recharge_service.dto.RechargeDTO;

@Service
public class RechargeService {
	private final RabbitTemplate rabbitTemplate;

	public RechargeService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	public String sendRechargeRequest(RechargeDTO request) {
		rabbitTemplate.convertAndSend(RabbitConfig.Queue_Name,request);
		return "Message sent to broker";
		
	}
}
