package com.rechargex.rechargeservice.service;

import com.rechargex.rechargeservice.client.OperatorServiceClient;
import com.rechargex.rechargeservice.client.PaymentResult;
import com.rechargex.rechargeservice.client.PaymentServiceClient;
import com.rechargex.rechargeservice.client.PlanDetails;
import com.rechargex.rechargeservice.dto.RechargeRequestDTO;
import com.rechargex.rechargeservice.entity.RechargeEntity;
import com.rechargex.rechargeservice.entity.enums.RechargeStatus;
import com.rechargex.rechargeservice.exception.PaymentFailedException;
import com.rechargex.rechargeservice.exception.PlanNotFoundException;
import com.rechargex.rechargeservice.repository.RechargeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RechargeServiceTest {

    @Mock
    private RechargeRepository rechargeRepository;

    @Mock
    private OperatorServiceClient operatorServiceClient;

    @Mock
    private PaymentServiceClient paymentServiceClient;

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private RechargeService rechargeService;

    @Test
    void shouldInitiateRecharge_whenValidRequest() {
        RechargeRequestDTO request = new RechargeRequestDTO("9876543210", 1L, 10L, "UPI");
        PlanDetails plan = PlanDetails.builder()
                .operatorId(1L)
                .planId(10L)
                .operatorName("Jio")
                .planName("1.5GB/day")
                .amount(BigDecimal.valueOf(299))
                .validityDays(28)
                .build();

        when(operatorServiceClient.validatePlan(1L, 10L)).thenReturn(plan);
        when(paymentServiceClient.charge(any(), any(), any(), any())).thenReturn(new PaymentResult(true, 99L, null));
        when(rechargeRepository.save(any(RechargeEntity.class))).thenAnswer(inv -> {
            RechargeEntity entity = inv.getArgument(0);
            entity.setId(123L);
            return entity;
        });

        rechargeService.initiateRecharge(request, 101L);

        verify(rechargeRepository, times(1)).save(any(RechargeEntity.class));
        ArgumentCaptor<RechargeCreatedInternalEvent> eventCaptor = ArgumentCaptor.forClass(RechargeCreatedInternalEvent.class);
        verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
        assertEquals(123L, eventCaptor.getValue().rechargeId());
    }

    @Test
    void shouldThrowException_whenPlanInvalid() {
        RechargeRequestDTO request = new RechargeRequestDTO("9876543210", 1L, 10L, "UPI");
        when(operatorServiceClient.validatePlan(1L, 10L)).thenThrow(new PlanNotFoundException("Plan not found"));

        assertThrows(PlanNotFoundException.class, () -> rechargeService.initiateRecharge(request, 101L));
        verify(rechargeRepository, never()).save(any());
        verify(eventPublisher, never()).publishEvent(any());
    }

    @Test
    void shouldThrowException_whenPaymentFails() {
        RechargeRequestDTO request = new RechargeRequestDTO("9876543210", 1L, 10L, "UPI");
        PlanDetails plan = PlanDetails.builder()
                .operatorId(1L)
                .planId(10L)
                .operatorName("Jio")
                .planName("1.5GB/day")
                .amount(BigDecimal.valueOf(299))
                .validityDays(28)
                .build();

        when(operatorServiceClient.validatePlan(1L, 10L)).thenReturn(plan);
        when(paymentServiceClient.charge(any(), any(), any(), any())).thenReturn(new PaymentResult(false, null, "declined"));

        assertThrows(PaymentFailedException.class, () -> rechargeService.initiateRecharge(request, 101L));

        ArgumentCaptor<RechargeEntity> entityCaptor = ArgumentCaptor.forClass(RechargeEntity.class);
        verify(rechargeRepository, times(1)).save(entityCaptor.capture());
        assertEquals(RechargeStatus.FAILED, entityCaptor.getValue().getStatus());
        verify(eventPublisher, never()).publishEvent(any());
    }
}

