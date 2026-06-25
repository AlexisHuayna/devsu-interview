package com.devsu.account_service.domain.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.devsu.account_service.domain.entity.Transaction;

public interface TransactionRepository {
    Transaction save(Transaction transaction);

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> 
        findByAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end
        );
    
    Optional<Transaction> findById(Long id);

    List<Transaction> findAll();
}
