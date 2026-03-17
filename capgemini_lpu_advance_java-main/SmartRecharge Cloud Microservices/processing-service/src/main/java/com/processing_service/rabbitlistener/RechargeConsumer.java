package com.processing_service.rabbitlistener;


import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.processing_service.config.RabbitConfig;
import com.processing_service.rechargedto.RechargeDTO;
import com.processing_service.rechargeentity.RechargeRequest;
import com.processing_service.rechargerepository.RechargeRepository;

@Component
public class RechargeConsumer {

    private final RechargeRepository repository;

    public RechargeConsumer(RechargeRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitConfig.Queue_Name)
    public void processrecharge(RechargeDTO dto) throws InterruptedException {

        if (repository.existsById(dto.getRequestId())) {
            System.out.println("Duplicate request ignored: " + dto.getRequestId());
            return;
        }
       
        RechargeRequest req = new RechargeRequest();
        if(dto.getAmount()<0) {
        	throw new RuntimeException("Failed request Invalid Amount"+dto.getAmount());
        }
        req.setRequestId(dto.getRequestId());
        req.setMobilenum(dto.getMobilenum());
        req.setAmount(dto.getAmount());
        req.setOperator(dto.getOperator());
        req.setStatus("SUCCESS");
        repository.save(req);
        

        System.out.println("Recharge processed for " + dto.getMobilenum());
    }
    
}