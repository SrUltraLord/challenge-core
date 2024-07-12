package com.ntt.core.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(indexes = {
        @Index(name = "idx_account_number", columnList = "number"),
        @Index(name = "idx_account_client_id", columnList = "client_id")
})
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String number;

    @Enumerated(EnumType.STRING)
    private AccountType type;

    private BigDecimal initialBalance;

    private Boolean status;

    private Long clientId;
}
