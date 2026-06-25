package com.devsu.account_service.application.dto.request;

import java.math.BigDecimal;

import com.devsu.account_service.domain.entity.AccountType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record CreateAccountRequest(
    
    @NotBlank(message = "accountNumber is required")
    @Size(max = 20, min = 5, message = "accountNumber must contain between 5 and 20 characters")
    String accountNumber,

    @NotNull(message = "accountType is required")
    AccountType accountType,

    @NotNull(message = "initialBalance is required")
    @PositiveOrZero(message = "initialBalance must be greater equal than zero")
    BigDecimal initialBalance,

    @NotNull(message = "customerId is required")
    @Positive(message = "customerId must be greater than zero")
    Long customerId
) {
    
}
