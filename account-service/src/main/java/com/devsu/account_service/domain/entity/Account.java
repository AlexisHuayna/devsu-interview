package com.devsu.account_service.domain.entity;

import java.math.BigDecimal;

import com.devsu.account_service.domain.exception.AccountInactiveException;
import com.devsu.account_service.domain.exception.InsufficientBalanceException;
import com.devsu.account_service.domain.exception.InvalidTransactionAmountException;

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
    private boolean active;

    @Column(nullable = false)
    private Long customerId;

    @Version
    private Long version;

    public Account(
        String accountNumber,
        AccountType accountType,
        BigDecimal initialBalance,
        Long customerId
    ){
        this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.initialBalance = initialBalance;
        this.availableBalance = initialBalance;
        this.active = true;
        this.customerId = customerId;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(
        boolean active
    ) {
        this.active = active;
    }

    public void deposit(
        BigDecimal amount
    ) {
        validateAmount(amount);

        this.availableBalance = this.availableBalance.add(amount);
    }

    public void withdraw(
        BigDecimal amount
    ) {
        validateAmount(amount);

        if(
            this.availableBalance.compareTo(amount) < 0
        ) {
            throw new InsufficientBalanceException();
        }

        this.availableBalance = this.availableBalance.subtract(amount);
    }

    public void activate() {
        this.active = true;
    }

    public void ensureActive() {
        if(!active) {
            throw new AccountInactiveException(accountNumber);
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidTransactionAmountException(amount);
        }
    }

}
