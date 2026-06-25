package com.devsu.account_service.domain.exception;

import com.devsu.account_service.domain.entity.TransactionType;

public class InvalidTransactionException extends RuntimeException {

    public InvalidTransactionException(
        TransactionType transactionType,
        String detail
    ){
        super(
            transactionType
            + " transactions must have a "
            + detail
            + " amount."
        );
    }
    
}
