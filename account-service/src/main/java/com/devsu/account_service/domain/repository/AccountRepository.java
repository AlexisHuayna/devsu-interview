package com.devsu.account_service.domain.repository;

import java.util.List;
import java.util.Optional;

import com.devsu.account_service.domain.entity.Account;

public interface AccountRepository {

    Account save(Account account);
    
    Optional<Account> findById(Long id);

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    List<Account> findByCustomerId(Long customerId);

    List<Account> findAll();

    List<Account> findByActiveTrue();
    
    void delete(Account customer);

    List<Account> findByIdIn(List<Long> ids);
}
