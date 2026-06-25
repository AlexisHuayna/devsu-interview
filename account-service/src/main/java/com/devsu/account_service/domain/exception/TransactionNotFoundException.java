package com.devsu.account_service.domain.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(
        Long id
    ){
        super(
            "Transaction with id "
            + id
            + " not found exception."
        );
    }
}
