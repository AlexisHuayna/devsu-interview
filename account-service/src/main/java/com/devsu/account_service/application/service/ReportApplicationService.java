package com.devsu.account_service.application.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.account_service.application.dto.response.AccountStatementResponse;
import com.devsu.account_service.application.mapper.ReportMapper;
import com.devsu.account_service.domain.entity.Account;
import com.devsu.account_service.domain.entity.CustomerSnapshot;
import com.devsu.account_service.domain.entity.Transaction;
import com.devsu.account_service.domain.repository.AccountRepository;
import com.devsu.account_service.domain.repository.CustomerSnapshotRepository;
import com.devsu.account_service.domain.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportApplicationService {
    
    private final CustomerSnapshotRepository customerSnapshotRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final ReportMapper reportMapper;

    public List<AccountStatementResponse> generateReport(
        Long customerId,
        LocalDate startDate,
        LocalDate endDate
    ){
        CustomerSnapshot customer = getRequiredCustomerSnapshot(customerId);
        List<Account> accounts = accountRepository.findByCustomerId(customerId);

        if(accounts.isEmpty()) {
            return List.of();
        }

        List<Long> accountIds = accounts.stream().map(Account::getId).toList();

        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        Map<Long, List<Transaction>> transactionsGroupedByAccount = 
            transactionRepository
                .findByAccountIdInAndTransactionDateBetween(
                    accountIds,
                    startDateTime,
                    endDateTime
                )
                .stream()
                .collect(
                    Collectors.groupingBy(Transaction::getAccountId)
                );

        return accounts
                .stream()
                .flatMap(account -> 
                    transactionsGroupedByAccount
                        .getOrDefault(
                            account.getId(),
                            List.of()
                        )
                        .stream()
                        .map(transaction -> 
                            reportMapper.toResponse(
                                customer,
                                account,
                                transaction
                            )
                        )
                )
                .toList();
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
}
