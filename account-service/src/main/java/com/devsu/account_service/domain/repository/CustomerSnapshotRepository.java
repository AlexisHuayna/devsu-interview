package com.devsu.account_service.domain.repository;

import java.util.List;
import java.util.Optional;

import com.devsu.account_service.domain.entity.CustomerSnapshot;

public interface CustomerSnapshotRepository {
    CustomerSnapshot save(CustomerSnapshot customerSnapshot);

    Optional<CustomerSnapshot> findByCustomerId(Long customerId);

    List<CustomerSnapshot> findByCustomerIdIn(List<Long> customerIds);

    boolean existsByCustomerId(Long customerId);
}
