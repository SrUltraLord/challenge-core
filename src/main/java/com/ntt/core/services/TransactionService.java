package com.ntt.core.services;

import com.ntt.core.dto.CreateTransactionDTO;
import com.ntt.core.dto.TransactionDTO;
import com.ntt.core.dto.TransactionDetailsDTO;
import com.ntt.core.dto.UpdateTransactionDTO;
import com.ntt.core.exceptions.AccountNotFoundException;
import com.ntt.core.exceptions.InsufficientFundsException;
import com.ntt.core.exceptions.TransactionNotFoundException;
import com.ntt.core.mappers.TransactionMapper;
import com.ntt.core.models.Account;
import com.ntt.core.models.Transaction;
import com.ntt.core.models.TransactionType;
import com.ntt.core.repositories.AccountRepository;
import com.ntt.core.repositories.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TransactionService {
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final ClientService clientService;

    public TransactionDTO findById(Long transactionId) {
        var transaction = findTransactionByIdOrThrowNotFound(transactionId);

        return transactionMapper.toDto(transaction);
    }

    public Map<String, BigDecimal> createTransaction(CreateTransactionDTO transactionDTO) {
        var account = findAccountByNumberOrThrowNotFound(transactionDTO.accountNumber());

        var initialBalance = account.getInitialBalance();
        var newBalance = initialBalance.add(transactionDTO.amount());

        var isNegativeAmount = transactionDTO.amount().compareTo(BigDecimal.ZERO) < 0;
        var withDrawExceedsAvailableBalance = newBalance.compareTo(BigDecimal.ZERO) < 0;
        if (isNegativeAmount && withDrawExceedsAvailableBalance) {
            throw new InsufficientFundsException("Account has insufficient funds for the required operation.");
        }

        var transaction = Transaction.builder()
                .account(account)
                .type(isNegativeAmount ? TransactionType.WITHDRAW : TransactionType.DEPOSIT)
                .amount(transactionDTO.amount())
                .balance(newBalance)
                .performedAt(LocalDate.now()) // Mock clock
                .status(true)
                .build();
        account.setInitialBalance(newBalance);

        accountRepository.save(account);
        transactionRepository.save(transaction);

        return Map.of("balance", newBalance);
    }

    public Map<String, Long> updateTransaction(Long id, UpdateTransactionDTO dto) {
        var transaction = findTransactionByIdOrThrowNotFound(id);
        transaction.setPerformedAt(dto.performedAt());

        transactionRepository.save(transaction);

        return Map.of("id", transaction.getId());
    }

    public Map<String, Long> deleteTransaction(Long id) {
        var transaction = findTransactionByIdOrThrowNotFound(id);
        transaction.setStatus(false);

        transactionRepository.save(transaction);

        return Map.of("id", transaction.getId());
    }

    public Map<String, List<TransactionDetailsDTO>> generateReport(Long clientId, List<LocalDate> dateRange) {
        var startDate = dateRange.get(0);
        var endDate = dateRange.get(1);
        var client = clientService.findClientById(clientId);
        var accountsIds = accountRepository.findAllByClientId(clientId)
                .stream().map(Account::getId).collect(Collectors.toSet());

        var transactions = transactionRepository.findAllByAccountIdInAndPerformedAtBetween(
                accountsIds, startDate, endDate);

        return Map.of("Movimientos", transactionMapper.toManyDetails(client, transactions));
    }

    private Transaction findTransactionByIdOrThrowNotFound(Long id) {
        return transactionRepository.findByIdAndStatus(id, true)
                .orElseThrow(() -> new TransactionNotFoundException("Could not find transaction with id " + id));
    }

    private Account findAccountByNumberOrThrowNotFound(String accountNumber) {
        return accountRepository.findByNumberAndStatus(accountNumber, true)
                .orElseThrow(() -> new AccountNotFoundException("Could not find active account for number " + accountNumber));
    }
}
