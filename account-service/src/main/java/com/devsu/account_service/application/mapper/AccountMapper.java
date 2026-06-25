package com.devsu.account_service.application.mapper;

import org.springframework.stereotype.Component;

import com.devsu.account_service.application.dto.response.AccountResponse;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.CustomerSnapshot;

@Component
public class AccountMapper {
    
    public AccountResponse toResponse(
        Account account,
        CustomerSnapshot customerSnapshot
    ) {
        return new AccountResponse(
            account.getId(),
            account.getAccountNumber(),
            account.getAccountType(),
            account.getInitialBalance(),
            account.getAvailableBalance(),
            account.isActive(),
            customerSnapshot.getName()
        );
    }
}
