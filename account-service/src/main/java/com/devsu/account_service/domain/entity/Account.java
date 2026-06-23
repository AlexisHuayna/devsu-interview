package com.devsu.account_service.domain.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "accounts",
    indexes = {
        @Index(
            name = "idx_account_customer",
            columnList = "customerId"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Account extends BaseEntity {
    @Column(
        nullable = false,
        unique = true,
        length = 20
    )
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountType accountType;

    @Column(
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal initialBalance;

    @Column(
        nullable = false,
        precision = 19,
        scale = 2
    )
    private BigDecimal availableBalance;

    @Column(nullable = false)
    private Boolean active;

    @Column(nullable = false)
    private Long customerId;

    @Version
    private Long version;
}
