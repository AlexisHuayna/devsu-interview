package com.devsu.account_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.account_service.domain.entity.Account;

public interface JpaAccountRepository 
        extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);

    List<Account> findByCustomerId(Long customerId);

    List<Account> findByActiveTrue();

    boolean existsByAccountNumber(String accountNumber);
    
    List<Account> findByIdIn(List<Long> ids);
}
