package com.devsu.account_service.application.dto.response;

import java.math.BigDecimal;

import com.devsu.account_service.domain.entity.AccountType;

public record AccountResponse(
    Long id,
    String accountNumber,
    AccountType accountType,
    BigDecimal initialBalance,
    BigDecimal availableBalance,
    boolean active,
    String client
) {
}
