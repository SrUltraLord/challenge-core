package com.ntt.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionDTO(
        @JsonProperty("NumeroCuenta")
        String accountNumber,

        @JsonProperty("Tipo")
        String accountType,

        @JsonProperty("Fecha")
        LocalDate performedAt,

        @JsonProperty("SaldoInicial")
        BigDecimal initialBalance,

        @JsonProperty("Movimiento")
        BigDecimal amount
) {
}
