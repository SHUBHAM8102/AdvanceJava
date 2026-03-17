package com.recharge_service.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.recharge_service.dto.RechargeDTO;
import com.recharge_service.service.RechargeService;

@RestController
public class RechargeController {

	private RechargeService service;

public RechargeController(RechargeService service) {
	super();
	this.service = service;
}

@PostMapping("/recharge")
public String dorecharge (@RequestBody RechargeDTO dto) {
	service.sendRechargeRequest(dto);
	return "Request Sent";
	
}
}
