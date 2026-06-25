package com.devsu.account_service.application.mapper;

import org.springframework.stereotype.Component;

import com.devsu.account_service.application.dto.response.TransactionResponse;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.Transaction;

@Component
public class TransactionMapper {

    public TransactionResponse toResponse(
        Transaction transaction,
        Account account
    ) {
        return new TransactionResponse(
            transaction.getId(),
            transaction.getTransactionDate(),
            transaction.getTransactionType(),
            transaction.getAmount(),
            transaction.getBalance(),
            account.getAccountNumber()
        );
    }
    
}
