package com.devsu.account_service.domain.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Setter
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
}
