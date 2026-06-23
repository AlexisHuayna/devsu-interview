package com.devsu.customer_service.application.mapper;

import org.springframework.stereotype.Component;

import com.devsu.customer_service.application.dto.response.CustomerResponse;
import com.devsu.customer_service.domain.entity.Customer;

@Component
public class CustomerMapper {
    
    public CustomerResponse toResponse(
        Customer customer
    ) {
        return new CustomerResponse(
            customer.getId(),
            customer.getName(),
            customer.getGender(),
            customer.getAge(),
            customer.getIdentification(),
            customer.getAddress(),
            customer.getPhone(),
            customer.isActive()
        );
    }
}
