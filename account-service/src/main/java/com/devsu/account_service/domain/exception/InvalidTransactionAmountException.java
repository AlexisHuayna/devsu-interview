package com.devsu.account_service.domain.exception;

import java.math.BigDecimal;

public class InvalidTransactionAmountException extends RuntimeException {
    public InvalidTransactionAmountException(BigDecimal amount) {
        super(
            "Transaction amount must be greater than zero. Received: "
            + amount
        );
    }
}
