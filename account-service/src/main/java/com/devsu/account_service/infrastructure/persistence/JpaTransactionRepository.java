package com.devsu.account_service.infrastructure.persistence;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.account_service.domain.entity.Transaction;

public interface JpaTransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountId(Long accountId);

    List<Transaction> 
        findByAccountIdAndTransactionDateBetween(
            Long accountId,
            LocalDateTime start,
            LocalDateTime end
        );

    List<Transaction>
        findByAccountIdInAndTransactionDateBetween(
            List<Long> accountIds,
            LocalDateTime start,
            LocalDateTime end
        );
    
}
