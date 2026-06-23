package com.devsu.customer_service.infrastructure.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devsu.customer_service.domain.entity.Customer;

public interface JpaCustomerRepository 
        extends JpaRepository<Customer, Long> {

    boolean existsByIdentification(String identification);

    List<Customer> findByActiveTrue();
    
}
