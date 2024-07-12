package com.ntt.core.controllers;

import com.ntt.core.dto.CreateTransactionDTO;
import com.ntt.core.dto.TransactionDTO;
import com.ntt.core.dto.TransactionDetailsDTO;
import com.ntt.core.dto.UpdateTransactionDTO;
import com.ntt.core.services.TransactionService;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movimientos")
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("{id}")
    public TransactionDTO findTransactionById(@PathVariable Long id) {
        return transactionService.findById(id);
    }

    @PostMapping
    public Map<String, BigDecimal> createTransaction(@RequestBody CreateTransactionDTO dto) {
        return transactionService.createTransaction(dto);
    }

    @PutMapping("{id}")
    public Map<String, Long> updateTransaction(@PathVariable Long id, @RequestBody UpdateTransactionDTO dto) {
        return transactionService.updateTransaction(id, dto);
    }

    @DeleteMapping("{id}")
    public Map<String, Long> deleteTransaction(@PathVariable Long id) {
        return transactionService.deleteTransaction(id);
    }
}
