package com.ntt.core.controllers;

import com.ntt.core.dto.TransactionDetailsDTO;
import com.ntt.core.services.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportsController {
    private final TransactionService transactionService;

    @GetMapping
    public Map<String, List<TransactionDetailsDTO>> generateTransactionReport(
            @RequestParam(name = "idCliente") Long clientId, @RequestParam(name = "fecha") List<LocalDate> dateRange) {
        return transactionService.generateReport(clientId, dateRange);
    }
}
