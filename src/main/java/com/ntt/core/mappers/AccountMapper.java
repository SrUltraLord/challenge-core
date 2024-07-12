package com.ntt.core.mappers;

import com.ntt.core.dto.AccountDTO;
import com.ntt.core.dto.CreateAccountDTO;
import com.ntt.core.dto.UpdateAccountDTO;
import com.ntt.core.models.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public Account toEntity(CreateAccountDTO dto) {
        return Account.builder()
                .number(dto.number())
                .type(dto.type())
                .initialBalance(dto.initialBalance())
                .clientId(dto.clientId())
                .build();
    }

    public Account toEntity(UpdateAccountDTO dto) {
        return Account.builder()
                .type(dto.type())
                .build();
    }

    public AccountDTO toDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId())
                .number(account.getNumber())
                .type(account.getType())
                .initialBalance(account.getInitialBalance())
                .build();
    }
}
