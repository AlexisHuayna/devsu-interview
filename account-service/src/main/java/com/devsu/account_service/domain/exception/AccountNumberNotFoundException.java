package com.devsu.account_service.domain.exception;

public class AccountNumberNotFoundException
        extends RuntimeException{
    
    public AccountNumberNotFoundException(
        String accountNumber
    ) {
        super(
            "Account with accountNumber "
            + accountNumber
            + " was not found."
        );
    }
    
}
