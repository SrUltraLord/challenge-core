package com.ntt.core.controllers;

import com.ntt.core.dto.AccountDTO;
import com.ntt.core.dto.CreateAccountDTO;
import com.ntt.core.dto.UpdateAccountDTO;
import com.ntt.core.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping("{number}")
    public AccountDTO findAccountByNumber(@PathVariable String number) {
        return accountService.findAccountByNumber(number);
    }

    @PostMapping
    public Map<String, Long> createAccount(@RequestBody CreateAccountDTO dto) {
        return accountService.createAccount(dto);
    }

    @PutMapping("{id}")
    public Map<String, Long> updateAccount(@PathVariable Long id, @RequestBody UpdateAccountDTO dto) {
        return accountService.updateAccount(id, dto);
    }

    @DeleteMapping("{id}")
    public Map<String, Long> deleteAccount(@PathVariable Long id) {
        return accountService.deleteAccountById(id);
    }
}
