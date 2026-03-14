package com.rechargex.rechargeservice.client;

import java.math.BigDecimal;

public interface PaymentServiceClient {
    PaymentResult charge(Long userId, BigDecimal amount, String paymentMethod, String transactionId);
}

