package com.devsu.account_service.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.account_service.application.dto.request.CreateTransactionRequest;
import com.devsu.account_service.application.dto.response.TransactionResponse;
import com.devsu.account_service.application.mapper.TransactionMapper;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.Transaction;
import com.devsu.account_service.domain.entity.TransactionType;
import com.devsu.account_service.domain.exception.AccountNotFoundException;
import com.devsu.account_service.domain.exception.AccountNumberNotFoundException;
import com.devsu.account_service.domain.exception.InvalidTransactionException;
import com.devsu.account_service.domain.exception.TransactionNotFoundException;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionApplicationService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionMapper transactionMapper;

    public TransactionResponse createTransaction(CreateTransactionRequest request) {
        validateTransactionRequest(request);

        Account account = getRequiredAccount(request.accountNumber());
        account.ensureActive();

        BigDecimal transactionAmount = request.amount();

        BigDecimal normalizedAmount = transactionAmount.abs();

        applyTransaction(
            account,
            request.transactionType(),
            normalizedAmount
        );
        
        Transaction transaction = new Transaction(
            request.transactionType(),
            transactionAmount,
            account.getAvailableBalance(),
            account.getId()
        );

        accountRepository.save(account);

        Transaction savedTransaction = transactionRepository.save(transaction);

        return transactionMapper.toResponse(savedTransaction, account);
        
    }

    @Transactional(readOnly = true)
    public TransactionResponse getTransaction(Long id) {
        Transaction transaction = transactionRepository
                .findById(id)
                .orElseThrow(
                    () -> new TransactionNotFoundException(id)
                );
        
        Account account = getRequiredAccount(transaction.getAccountId());

        return transactionMapper.toResponse(transaction, account);

    }

    @Transactional(readOnly = true)
    public List<TransactionResponse> getTransactions() {
        List<Transaction> transactions = transactionRepository.findAll();

        if(transactions.isEmpty()){
            return List.of();
        }

        List<Long> accountIds = transactions
                .stream()
                .map(Transaction::getAccountId)
                .distinct()
                .toList();
        
        Map<Long, Account> accounts = accountRepository.findByIdIn(accountIds)
                .stream()
                .collect(
                    Collectors.toUnmodifiableMap(
                            Account::getId,
                            Function.identity()
                    )
                );

        return transactions
                .stream()
                .map(transaction -> {
                    Account account = accounts.get(transaction.getAccountId());

                    if(account == null) {
                        throw new IllegalStateException(
                            "Account not found for transctionId="
                            + transaction.getId()
                        );
                    }

                    return transactionMapper.toResponse(transaction, account);
                })
                .toList();
    }

    private Account getRequiredAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(
                    () -> new AccountNumberNotFoundException(accountNumber)
                );
    }

    private Account getRequiredAccount(Long accountId) {
        return accountRepository.findById(accountId)
                .orElseThrow(
                    () -> new AccountNotFoundException(accountId)
                );
    }

    private void validateTransactionRequest(
        CreateTransactionRequest request
    ) {
        boolean positive =
            request.amount().compareTo(BigDecimal.ZERO) > 0;

        boolean negative =
            request.amount().compareTo(BigDecimal.ZERO) < 0;

        if( 
            request.transactionType() == TransactionType.DEPOSIT &&
            !positive
        ){
            throw new InvalidTransactionException(TransactionType.DEPOSIT, "positive");
        }

        if (
            request.transactionType() == TransactionType.WITHDRAWAL &&
            !negative
        ) {
            throw new InvalidTransactionException(TransactionType.WITHDRAWAL, "negative");
        }
    }
    
    private void applyTransaction(
        Account account,
        TransactionType transactionType,
        BigDecimal amount
    ){
        switch (transactionType) {
            case DEPOSIT:
                account.deposit(amount);
                break;

            case WITHDRAWAL:
                account.withdraw(amount);
                break;

            default:
                throw new IllegalStateException(
                    "Unexpected transaction type"
                );
        }
    }
}
