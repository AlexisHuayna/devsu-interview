package com.devsu.account_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.repository.AccountRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

    private final JpaAccountRepository repository;

    @Override
    public Account save(Account account) {
        return repository.save(account);
    }

    @Override
    public Optional<Account> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public Optional<Account> findByAccountNumber(String accountNumber) {
        return repository.findByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public List<Account> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Account customer) {
        repository.delete(customer);
    }

    @Override
    public List<Account> findByActiveTrue() {
        return repository.findByActiveTrue();
    }

    @Override
    public boolean existsByAccountNumber(String accountNumber) {
        return repository.existsByAccountNumber(accountNumber);
    }

    @Override
    public List<Account> findByIdIn(List<Long> ids) {
        return repository.findByIdIn(ids);
    }
    
}
