package com.devsu.account_service.application.service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.account_service.application.dto.request.CreateAccountRequest;
import com.devsu.account_service.application.dto.request.UpdateAccountRequest;
import com.devsu.account_service.application.dto.response.AccountResponse;
import com.devsu.account_service.application.mapper.AccountMapper;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.CustomerSnapshot;
import com.devsu.account_service.domain.exception.AccountNotFoundException;
import com.devsu.account_service.domain.exception.AccountNumberAlreadyExistsException;
import com.devsu.account_service.domain.exception.CustomerInactiveException;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.repository.CustomerSnapshotRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class AccountApplicationService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final CustomerSnapshotRepository customerSnapshotRepository;

    public AccountResponse createAccount(CreateAccountRequest request) {
        CustomerSnapshot customerSnapshot = getActiveCustomerSnapshot(request.customerId());
        
        if(accountRepository.existsByAccountNumber(request.accountNumber())) {
            throw new AccountNumberAlreadyExistsException(request.accountNumber());
        }

        Account account = new Account(
            request.accountNumber(),
            request.accountType(),
            request.initialBalance(),
            request.customerId()
        );

        Account savedAccount = accountRepository.save(account);

        return accountMapper.toResponse(savedAccount, customerSnapshot);

    }

    @Transactional(readOnly = true)
    public AccountResponse getAccountById(Long id) {
        Account account = getRequiredAccount(id);
        
        CustomerSnapshot customerSnapshot = getRequiredCustomerSnapshot(account.getCustomerId());
        
        return accountMapper.toResponse(account, customerSnapshot);
    }

    @Transactional(readOnly = true)
    public List<AccountResponse> getAccounts() {
        List<Account> accounts = accountRepository.findByActiveTrue();

        if(accounts.isEmpty()) {
            return List.of();
        }

        List<Long> customerIds = accounts
                .stream()
                .map(Account::getCustomerId)
                .distinct()
                .toList();
        
        Map<Long, CustomerSnapshot> snapshots = customerSnapshotRepository
                    .findByCustomerIdIn(customerIds)
                    .stream()
                    .collect(
                        Collectors.toUnmodifiableMap(
                            CustomerSnapshot::getCustomerId,
                            Function.identity()
                        )
                    );
        
        return accounts
                .stream()
                .map(account -> {
                    CustomerSnapshot customer = snapshots.get(account.getCustomerId());

                    if(customer == null) {
                        throw new IllegalStateException(
                            "Customer snapshot not found for customerId="
                            + account.getCustomerId()
                        );
                    }

                    return accountMapper.toResponse(account, customer);
                })
                .toList();
    }

    public AccountResponse updateAccount(Long accountId, UpdateAccountRequest request) {
        Account account = getRequiredAccount(accountId);

        CustomerSnapshot customerSnapshot = getRequiredCustomerSnapshot(account.getCustomerId());
        
        account.update(
            request.active()
        );

        return accountMapper.toResponse(account, customerSnapshot);
    }

    public void deleteAccount(Long accountId) {
        Account account = getRequiredAccount(accountId);
        
        // CustomerSnapshot customerSnapshot = getRequiredCustomerSnapshot(account.getCustomerId());
        
        account.deactivate();

        // return accountMapper.toResponse(account, customerSnapshot);
    }

    private Account getRequiredAccount(Long accountId) {
        return accountRepository
                .findById(accountId)
                .orElseThrow(
                    () -> new AccountNotFoundException(accountId)
                );
    }

    private CustomerSnapshot getRequiredCustomerSnapshot(Long customerId) {
        return customerSnapshotRepository
                .findByCustomerId(customerId)
                .orElseThrow(
                    () ->   new IllegalStateException(
                            "Customer snapshot not found for customerId="
                            + customerId
                        )
                );
    }

    private CustomerSnapshot getActiveCustomerSnapshot(Long customerId) {
        CustomerSnapshot customer = getRequiredCustomerSnapshot(customerId);

        if(!customer.isActive()) {
            throw new CustomerInactiveException(customerId);
        }

        return customer;
    }
}
