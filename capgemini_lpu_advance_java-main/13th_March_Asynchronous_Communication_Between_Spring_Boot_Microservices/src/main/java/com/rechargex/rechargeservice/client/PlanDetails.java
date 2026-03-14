package com.rechargex.rechargeservice.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanDetails {
    private Long operatorId;
    private Long planId;
    private String operatorName;
    private String planName;
    private BigDecimal amount;
    private Integer validityDays;
}

