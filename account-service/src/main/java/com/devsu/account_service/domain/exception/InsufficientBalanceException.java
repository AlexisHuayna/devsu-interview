package com.devsu.account_service.domain.exception;

public class InsufficientBalanceException
        extends RuntimeException {
        
    public InsufficientBalanceException() {
        super("Unavailable balance");
    }
    
}
