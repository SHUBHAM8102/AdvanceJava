package com.rechargex.rechargeservice.dto;

import com.rechargex.rechargeservice.entity.RechargeEntity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class RechargeSuccessEvent {

    @NotNull
    private Long rechargeId;

    @NotNull
    private Long userId;

    @NotBlank
    private String mobileNumber;

    @NotBlank
    private String operatorName;

    @NotBlank
    private String planName;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private Integer validityDays;

    @Email
    @NotBlank
    private String userEmail;

    @NotBlank
    private String transactionId;

    @NotNull
    private LocalDateTime rechargedAt;

    public static RechargeSuccessEvent fromEntity(RechargeEntity entity, String userEmail, String operatorName, String planName, Integer validityDays) {
        return RechargeSuccessEvent.builder()
                .rechargeId(entity.getId())
                .userId(entity.getUserId())
                .mobileNumber(entity.getMobileNumber())
                .operatorName(operatorName)
                .planName(planName)
                .amount(entity.getAmount())
                .validityDays(validityDays)
                .userEmail(userEmail)
                .transactionId(entity.getTransactionId())
                .rechargedAt(entity.getCompletedAt())
                .build();
    }
}

