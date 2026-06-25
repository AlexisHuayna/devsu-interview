package com.devsu.account_service.application.dto.request;

import java.math.BigDecimal;

import com.devsu.account_service.domain.entity.TransactionType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateTransactionRequest(

    @NotBlank(message = "accountNumber is required")
    @Size(
        max = 20,
        message = "accountNumber must no exceed 20 characteres"
    )
    String accountNumber,
    
    @NotNull(message = "transactionType is required")
    TransactionType transactionType,

    @NotNull(message = "amount is required")
    BigDecimal amount
) {
}
