package com.devsu.account_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsu.account_service.domain.entity.CustomerSnapshot;
import com.devsu.account_service.domain.repository.CustomerSnapshotRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerSnapshotRepositoryImpl implements CustomerSnapshotRepository {

    private final JpaCustomerSnapshotRepository repository;

    @Override
    public Optional<CustomerSnapshot> findByCustomerId(Long customerId) {
        return repository.findByCustomerId(customerId);
    }

    @Override
    public List<CustomerSnapshot> findByCustomerIdIn(List<Long> customerIds) {
        return repository.findByCustomerIdIn(customerIds);
    }

    @Override
    public CustomerSnapshot save(CustomerSnapshot customerSnapshot) {
        return repository.save(customerSnapshot);
    }

    @Override
    public boolean existsByCustomerId(Long customerId) {
        return repository.existsByCustomerId(customerId);
    }
    
}
