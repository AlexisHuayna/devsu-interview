package com.devsu.account_service.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.account_service.application.dto.request.CreateTransactionRequest;
import com.devsu.account_service.application.dto.response.TransactionResponse;
import com.devsu.account_service.application.service.TransactionApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("api/v1/transactions")
@RequiredArgsConstructor
@Validated
public class TransactionController {
    
    private final TransactionApplicationService transactionApplicationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(
        @Valid
        @RequestBody
        CreateTransactionRequest request
    ) {
        return transactionApplicationService.createTransaction(request);
    }

    @GetMapping("/{transactionId}")
    public TransactionResponse getTransaction(
        @PathVariable
        Long transactionId
    ) {
        return transactionApplicationService.getTransaction(transactionId);
    }
    
    @GetMapping()
    public List<TransactionResponse> getTransactions() {
        return transactionApplicationService.getTransactions();
    }
    
    
}
