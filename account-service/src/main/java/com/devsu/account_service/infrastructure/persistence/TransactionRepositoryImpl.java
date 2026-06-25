package com.devsu.account_service.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsu.account_service.domain.entity.Transaction;
import com.devsu.account_service.domain.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TransactionRepositoryImpl implements TransactionRepository{

    private final JpaTransactionRepository repository;

    @Override
    public Transaction save(Transaction transaction) {
        return repository.save(transaction);
    }

    @Override
    public List<Transaction> findByAccountId(Long accountId) {
        return repository.findByAccountId(accountId);
    }

    @Override
    public List<Transaction> findByAccountIdAndTransactionDateBetween(
        Long accountId,
        LocalDateTime start,
        LocalDateTime end
    ) {
        return repository.findByAccountIdAndTransactionDateBetween(accountId, start, end);
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Transaction> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Transaction> findByAccountIdInAndTransactionDateBetween(
        List<Long> accountIds,
        LocalDateTime start,
        LocalDateTime end
    ) {
        return repository.findByAccountIdInAndTransactionDateBetween(accountIds, start, end);
    }
    
}
