package com.devsu.customer_service.infrastructure.persistence;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.devsu.customer_service.domain.entity.Customer;
import com.devsu.customer_service.domain.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerRepositoryImpl implements CustomerRepository {

    private final JpaCustomerRepository repository;
    
    @Override
    public Customer save(Customer customer) {
        return repository.save(customer);
    }

    @Override
    public Optional<Customer> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Customer> findAll() {
        return repository.findAll();
    }

    @Override
    public void delete(Customer customer) {
        repository.delete(customer);
    }

    @Override
    public boolean existsByIdentification(String identification) {
        return repository.existsByIdentification(identification);
    }

    @Override
    public List<Customer> findByActiveTrue() {
        return repository.findByActiveTrue();
    }

    @Override
    public void deleteAll() {
        repository.deleteAll();
    }
    
}
