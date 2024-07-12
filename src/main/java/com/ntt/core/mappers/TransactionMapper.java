package com.ntt.core.mappers;

import com.ntt.core.dto.ClientDTO;
import com.ntt.core.dto.TransactionDTO;
import com.ntt.core.dto.TransactionDetailsDTO;
import com.ntt.core.models.AccountType;
import com.ntt.core.models.Transaction;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionMapper {
    public TransactionDTO toDto(Transaction transaction) {
        return TransactionDTO.builder()
                .accountNumber(transaction.getAccount().getNumber())
                .accountType(getAccountTypeValue(transaction.getAccount().getType()))
                .performedAt(transaction.getPerformedAt())
                .initialBalance(transaction.getBalance())
                .amount(transaction.getAmount())
                .build();
    }

    public TransactionDetailsDTO toDetails(ClientDTO client, Transaction transaction) {
        return TransactionDetailsDTO.builder()
                .accountNumber(transaction.getAccount().getNumber())
                .clientName(client.name())
                .accountType(getAccountTypeValue(transaction.getAccount().getType()))
                .performedAt(transaction.getPerformedAt())
                .status(transaction.getStatus())
                .initialBalance(transaction.getBalance().subtract(transaction.getAmount()))
                .amount(transaction.getAmount())
                .availableBalance(transaction.getBalance())
                .build();
    }

    public List<TransactionDetailsDTO> toManyDetails(ClientDTO client, List<Transaction> transactions) {
        return transactions.stream().map(t -> toDetails(client, t)).toList();
    }

    private String getAccountTypeValue(AccountType type) {
        return switch (type) {
            case CURRENT -> "Corriente";
            case SAVINGS -> "Ahorros";
        };
    }
}
