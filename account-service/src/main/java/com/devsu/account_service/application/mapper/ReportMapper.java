package com.devsu.account_service.application.mapper;

import org.springframework.stereotype.Component;

import com.devsu.account_service.application.dto.response.AccountStatementResponse;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.CustomerSnapshot;
import com.devsu.account_service.domain.entity.Transaction;

@Component
public class ReportMapper {
    
    public AccountStatementResponse toResponse(
        CustomerSnapshot customer,
        Account account,
        Transaction transaction
    ){
        return new AccountStatementResponse(
            transaction.getTransactionDate(),
            customer.getName(),
            account.getAccountNumber(),
            account.getAccountType(),
            account.getInitialBalance(),
            account.isActive(),
            transaction.getAmount(),
            transaction.getBalance()
        );
    }
}
