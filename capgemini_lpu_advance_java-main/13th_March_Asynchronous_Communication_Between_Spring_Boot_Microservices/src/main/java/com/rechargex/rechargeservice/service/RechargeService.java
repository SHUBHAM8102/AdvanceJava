package com.rechargex.rechargeservice.service;

import com.rechargex.rechargeservice.client.OperatorServiceClient;
import com.rechargex.rechargeservice.client.PaymentResult;
import com.rechargex.rechargeservice.client.PaymentServiceClient;
import com.rechargex.rechargeservice.client.PlanDetails;
import com.rechargex.rechargeservice.dto.RechargeRequestDTO;
import com.rechargex.rechargeservice.dto.RechargeResponseDTO;
import com.rechargex.rechargeservice.entity.RechargeEntity;
import com.rechargex.rechargeservice.entity.enums.RechargeStatus;
import com.rechargex.rechargeservice.exception.PaymentFailedException;
import com.rechargex.rechargeservice.repository.RechargeRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class RechargeService {

    private final RechargeRepository rechargeRepository;
    private final OperatorServiceClient operatorServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final ApplicationEventPublisher eventPublisher;

    public RechargeService(RechargeRepository rechargeRepository,
                           OperatorServiceClient operatorServiceClient,
                           PaymentServiceClient paymentServiceClient,
                           ApplicationEventPublisher eventPublisher) {
        this.rechargeRepository = rechargeRepository;
        this.operatorServiceClient = operatorServiceClient;
        this.paymentServiceClient = paymentServiceClient;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public RechargeResponseDTO initiateRecharge(RechargeRequestDTO request, Long userId) {
        PlanDetails planDetails = operatorServiceClient.validatePlan(request.getOperatorId(), request.getPlanId());

        String transactionId = "TXN-" + UUID.randomUUID();
        PaymentResult paymentResult = paymentServiceClient.charge(userId, planDetails.getAmount(), request.getPaymentMethod(), transactionId);
        if (!paymentResult.isSuccess()) {
            RechargeEntity failedEntity = RechargeEntity.builder()
                    .userId(userId)
                    .mobileNumber(request.getMobileNumber())
                    .operatorId(request.getOperatorId())
                    .planId(request.getPlanId())
                    .amount(planDetails.getAmount())
                    .status(RechargeStatus.FAILED)
                    .transactionId(transactionId)
                    .failureReason(paymentResult.getFailureReason())
                    .completedAt(LocalDateTime.now())
                    .eventPublished(false)
                    .build();
            rechargeRepository.save(failedEntity);
            throw new PaymentFailedException(paymentResult.getFailureReason() == null ? "Payment failed" : paymentResult.getFailureReason());
        }

        RechargeEntity entity = RechargeEntity.builder()
                .userId(userId)
                .mobileNumber(request.getMobileNumber())
                .operatorId(request.getOperatorId())
                .planId(request.getPlanId())
                .amount(planDetails.getAmount())
                .status(RechargeStatus.SUCCESS)
                .transactionId(transactionId)
                .paymentId(paymentResult.getPaymentId())
                .completedAt(LocalDateTime.now())
                .eventPublished(false)
                .build();

        RechargeEntity saved = rechargeRepository.save(entity);
        eventPublisher.publishEvent(new RechargeCreatedInternalEvent(saved.getId(), userId + "@user.local", planDetails));

        return RechargeResponseDTO.builder()
                .rechargeId(saved.getId())
                .transactionId(saved.getTransactionId())
                .status(RechargeStatus.PROCESSING.name())
                .message("Recharge accepted and notification will be sent asynchronously")
                .amount(saved.getAmount())
                .initiatedAt(saved.getInitiatedAt())
                .build();
    }
}
