package com.devsu.account_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.account_service.domain.entity.CustomerSnapshot;

public interface JpaCustomerSnapshotRepository
        extends JpaRepository<CustomerSnapshot,Long>{
    
    Optional<CustomerSnapshot> findByCustomerId(Long customerId);

    List<CustomerSnapshot> findByCustomerIdIn(List<Long> customerIds);

    boolean existsByCustomerId(Long customerId);
}
