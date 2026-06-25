package com.devsu.account_service.application.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.devsu.account_service.domain.entity.TransactionType;

public record TransactionResponse(
    Long id,
    LocalDateTime transactionDate,
    TransactionType transactionType,
    BigDecimal amount,
    BigDecimal balance,
    String accountNumber
) {
    
}
