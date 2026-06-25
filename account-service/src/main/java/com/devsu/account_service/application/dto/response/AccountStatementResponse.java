package com.devsu.account_service.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.devsu.account_service.domain.entity.AccountType;

public record AccountStatementResponse(
    LocalDateTime date,
    String customer,
    String accountNumber,
    AccountType accountType,
    BigDecimal initialBalance,
    boolean active,
    BigDecimal movement,
    BigDecimal availableBalance
) {
    
}
