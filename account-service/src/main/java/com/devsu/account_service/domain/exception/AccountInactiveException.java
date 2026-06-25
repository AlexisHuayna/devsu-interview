package com.devsu.account_service.domain.exception;

public class AccountInactiveException extends RuntimeException {

    public AccountInactiveException(String accountNumber) {
        super(
            "Account "
            + accountNumber
            + " is inactive."
        );
    }
    
}
