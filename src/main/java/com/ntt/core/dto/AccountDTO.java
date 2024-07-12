package com.ntt.core.dto;

import com.ntt.core.models.AccountType;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountDTO(
        Long id,
        String number,
        AccountType type,
        BigDecimal initialBalance
) {
}
