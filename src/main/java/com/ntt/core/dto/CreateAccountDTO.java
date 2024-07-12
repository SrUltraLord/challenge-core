package com.ntt.core.dto;

import com.ntt.core.models.AccountType;

import java.math.BigDecimal;

public record CreateAccountDTO(
        String number,

        AccountType type,

        BigDecimal initialBalance,

        Long clientId
) {
}
