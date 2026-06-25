package com.devsu.customer_service.application.service;

import java.util.List;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsu.customer_service.application.dto.request.CreateCustomerRequest;
import com.devsu.customer_service.application.dto.request.UpdateCustomerRequest;
import com.devsu.customer_service.application.dto.response.CustomerResponse;
import com.devsu.customer_service.application.event.CustomerCreatedInternalEvent;
import com.devsu.customer_service.application.event.CustomerDeactivatedInternalEvent;
import com.devsu.customer_service.application.event.CustomerUpdatedInternalEvent;
import com.devsu.customer_service.application.mapper.CustomerMapper;
import com.devsu.customer_service.domain.entity.Customer;
import com.devsu.customer_service.domain.exception.CustomerAlreadyExistsException;
import com.devsu.customer_service.domain.exception.CustomerNotFoundException;
import com.devsu.customer_service.domain.repository.CustomerRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerApplicationService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        if (customerRepository.existsByIdentification(customerRequest.identification())){
            throw new CustomerAlreadyExistsException(customerRequest.identification());
        }

        Customer customer = new Customer(
            customerRequest.name(),
            customerRequest.gender(),
            customerRequest.age(),
            customerRequest.identification(),
            customerRequest.address(),
            customerRequest.phone(),
            customerRequest.password(),
            customerRequest.active()
        );

        Customer savedCustomer = customerRepository.save(customer);

        applicationEventPublisher.publishEvent(
            new CustomerCreatedInternalEvent(
                savedCustomer.getId(),
                savedCustomer.getName(),
                savedCustomer.isActive()
            )
        );
        
        return customerMapper.toResponse(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                () -> new CustomerNotFoundException(customerId)
            );

        return customerMapper.toResponse(customer);
    }

    @Transactional(readOnly = true)
    public List<CustomerResponse> getCustomers() {
        return customerRepository
            .findByActiveTrue()
            .stream()
            .map(customerMapper::toResponse)
            .toList();
    }

    public CustomerResponse updateCustomer(Long customerId, UpdateCustomerRequest request) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                () -> new CustomerNotFoundException(customerId)
            );
        
        customer.update(
            request.name(),
            request.gender(),
            request.age(),
            request.address(),
            request.phone(),
            request.password(),
            request.active()           
        );

        applicationEventPublisher.publishEvent(
            new CustomerUpdatedInternalEvent(
                customer.getId(),
                customer.getName(),
                customer.isActive()
            )
        );

        return customerMapper.toResponse(customer);
    }

    public CustomerResponse deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
            .orElseThrow(
                () -> new CustomerNotFoundException(customerId)
            );

        customer.deactivate();

        applicationEventPublisher.publishEvent(
            new CustomerDeactivatedInternalEvent(
                customer.getId()
            )
        );

        return customerMapper.toResponse(customer);
    }
    
}
