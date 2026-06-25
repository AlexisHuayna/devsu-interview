package com.devsu.account_service.domain.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.devsu.account_service.domain.exception.AccountInactiveException;
import com.devsu.account_service.domain.exception.InsufficientBalanceException;
import com.devsu.account_service.domain.exception.InvalidTransactionAmountException;

class AccountTest {

    private Account createAccount() {
        return new Account(
                "478758",
                AccountType.SAVINGS,
                new BigDecimal("1000.00"),
                1L
        );
    }

    @Nested
    class DepositTests {

        @Test
        void shouldIncreaseAvailableBalance() {

            // Arrange
            Account account = createAccount();

            // Act
            account.deposit(new BigDecimal("500.00"));

            // Assert
            assertAll(
                    () -> assertEquals(
                            new BigDecimal("1500.00"),
                            account.getAvailableBalance()
                    ),
                    () -> assertEquals(
                            new BigDecimal("1000.00"),
                            account.getInitialBalance()
                    )
            );
        }

        @Test
        void shouldRejectNegativeDepositAmount() {

            // Arrange
            Account account = createAccount();

            // Act & Assert
            assertThrows(
                    InvalidTransactionAmountException.class,
                    () -> account.deposit(
                            new BigDecimal("-100")
                    )
            );
        }

        @Test
        void shouldRejectZeroDepositAmount() {

            // Arrange
            Account account = createAccount();

            // Act & Assert
            assertThrows(
                    InvalidTransactionAmountException.class,
                    () -> account.deposit(
                            BigDecimal.ZERO
                    )
            );
        }
    }

    @Nested
    class WithdrawalTests {

        @Test
        void shouldDecreaseAvailableBalance() {

            // Arrange
            Account account = createAccount();

            // Act
            account.withdraw(
                    new BigDecimal("250")
            );

            // Assert
            assertEquals(
                    new BigDecimal("750.00"),
                    account.getAvailableBalance()
            );
        }

        @Test
        void shouldThrowExceptionWhenBalanceIsInsufficient() {

            // Arrange
            Account account = createAccount();

            // Act & Assert
            assertThrows(
                    InsufficientBalanceException.class,
                    () -> account.withdraw(
                            new BigDecimal("5000")
                    )
            );
        }

        @Test
        void shouldRejectNegativeWithdrawalAmount() {

            // Arrange
            Account account = createAccount();

            // Act & Assert
            assertThrows(
                    InvalidTransactionAmountException.class,
                    () -> account.withdraw(
                            new BigDecimal("-10")
                    )
            );
        }

        @Test
        void shouldRejectZeroWithdrawalAmount() {

            // Arrange
            Account account = createAccount();

            // Act & Assert
            assertThrows(
                    InvalidTransactionAmountException.class,
                    () -> account.withdraw(
                            BigDecimal.ZERO
                    )
            );
        }
    }

    @Nested
    class ActivationTests {

        @Test
        void shouldDeactivateAccount() {

            // Arrange
            Account account = createAccount();

            // Act
            account.deactivate();

            // Assert
            assertFalse(account.isActive());
        }

        @Test
        void shouldActivateAccount() {

            // Arrange
            Account account = createAccount();
            account.deactivate();

            // Act
            account.activate();

            // Assert
            assertTrue(account.isActive());
        }

        @Test
        void shouldThrowExceptionWhenAccountIsInactive() {

            // Arrange
            Account account = createAccount();
            account.deactivate();

            // Act & Assert
            assertThrows(
                    AccountInactiveException.class,
                    account::ensureActive
            );
        }
    }
}