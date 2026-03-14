package com.rechargex.rechargeservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RechargeResponseDTO {
    private Long rechargeId;
    private String transactionId;
    private String status;
    private String message;
    private BigDecimal amount;
    private LocalDateTime initiatedAt;
}

