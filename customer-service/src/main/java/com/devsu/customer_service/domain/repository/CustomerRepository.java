package com.devsu.customer_service.domain.repository;

import java.util.List;
import java.util.Optional;

import com.devsu.customer_service.domain.entity.Customer;

public interface CustomerRepository {
    
    Customer save(Customer customer);

    Optional<Customer> findById(Long id);

    List<Customer> findAll();
    
    void delete(Customer customer);

    boolean existsByIdentification(String identification);

    List<Customer> findByActiveTrue();
}
