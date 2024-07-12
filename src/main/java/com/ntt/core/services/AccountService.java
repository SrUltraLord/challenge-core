package com.ntt.core.services;

import com.ntt.core.dto.AccountDTO;
import com.ntt.core.dto.CreateAccountDTO;
import com.ntt.core.dto.UpdateAccountDTO;
import com.ntt.core.exceptions.AccountAlreadyTakenException;
import com.ntt.core.exceptions.AccountNotFoundException;
import com.ntt.core.mappers.AccountMapper;
import com.ntt.core.models.Account;
import com.ntt.core.repositories.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDTO findAccountByNumber(String accountNumber) {
        var account = findByNumberOrThrowNotFound(accountNumber);
        return accountMapper.toDto(account);
    }

    public Map<String, Long> createAccount(CreateAccountDTO dto) {
        boolean numberAlreadyTaken = accountRepository.existsByNumber(dto.number());
        if (numberAlreadyTaken) {
            throw new AccountAlreadyTakenException("Provided account number is already taken.");
        }

        var newAccount = accountMapper.toEntity(dto);
        newAccount.setInitialBalance(dto.initialBalance() != null ? dto.initialBalance() : BigDecimal.ZERO);
        newAccount.setStatus(true);

        var createdAccount = accountRepository.save(newAccount);

        return Map.of("id", createdAccount.getId());
    }

    public Map<String, Long> updateAccount(Long accountId, UpdateAccountDTO dto) {
        var account = findByIdOrThrowNotFound(accountId);
        account.setType(dto.type());

        accountRepository.save(account);

        return Map.of("id", account.getId());
    }

    public Map<String, Long> deleteAccountById(Long accountId) {
        var account = findByIdOrThrowNotFound(accountId);
        account.setStatus(false);

        var deletedAccount = accountRepository.save(account);

        return Map.of("id", deletedAccount.getId());
    }

    private Account findByIdOrThrowNotFound(Long accountId) {
        return accountRepository.findByIdAndStatus(accountId, true)
                .orElseThrow(() -> new AccountNotFoundException("Could not find active account with id " + accountId));
    }

    private Account findByNumberOrThrowNotFound(String accountNumber) {
        return accountRepository.findByNumberAndStatus(accountNumber, true)
                .orElseThrow(() -> new AccountNotFoundException("Could not find an active account with number " + accountNumber));
    }
}
