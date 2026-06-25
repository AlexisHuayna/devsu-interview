package com.devsu.account_service.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.devsu.account_service.domain.exception.InvalidTransactionAmountException;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "transactions",
    indexes = {
        @Index(
            name="idx_transactions_account_transactionDate",
            columnList = "accountId,transactionDate"
        )
    }
)
@Getter
@NoArgsConstructor
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @Column(
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal amount;

    @Column(
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal balance;

    @Column(nullable = false)
    private Long accountId;

    public Transaction(
        TransactionType transactionType,
        BigDecimal amount,
        BigDecimal balance,
        Long accountId
    ) {

        Objects.requireNonNull(transactionType);
        Objects.requireNonNull(amount);
        Objects.requireNonNull(balance);
        Objects.requireNonNull(accountId);

        if(amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException(amount);
        }

        this.transactionDate = LocalDateTime.now();
        this.transactionType = transactionType;
        this.amount = amount;
        this.balance = balance;
        this.accountId = accountId;
    }
}
