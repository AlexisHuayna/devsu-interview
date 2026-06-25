package com.devsu.account_service.domain.exception;

public class AccountNumberAlreadyExistsException
        extends RuntimeException {

    public AccountNumberAlreadyExistsException(
        String accountNumber
    ) {
        super(
            "Account with number "
            + accountNumber
            + " already exists."
        );
    }
    
}