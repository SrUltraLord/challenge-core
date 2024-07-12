package com.ntt.core.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionDetailsDTO(
        @JsonProperty("NumeroCuenta")
        String accountNumber,

        @JsonProperty("Cliente")
        String clientName,

        @JsonProperty("Tipo")
        String accountType,

        @JsonProperty("Fecha")
        LocalDate performedAt,

        @JsonProperty("Estado")
        Boolean status,

        @JsonProperty("SaldoInicial")
        BigDecimal initialBalance,

        @JsonProperty("Movimiento")
        BigDecimal amount,

        @JsonProperty("SaldoDisponible")
        BigDecimal availableBalance
) {
}
