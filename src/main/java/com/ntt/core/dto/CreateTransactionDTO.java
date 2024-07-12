package com.ntt.core.dto;

import java.math.BigDecimal;

public record CreateTransactionDTO(String accountNumber, BigDecimal amount) {
}
