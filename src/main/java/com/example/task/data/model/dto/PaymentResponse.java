package com.example.task.data.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class PaymentResponse {

    private BigDecimal cash;

    private BigDecimal nonCash;

    private BigDecimal lastOperationBonus;

    private BigDecimal totalBonusAccount;

    private BigDecimal lastOperationCommission;

    private BigDecimal totalCommissionAccount;

}
