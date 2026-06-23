package com.devsu.customer_service.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.customer_service.application.dto.request.CreateCustomerRequest;
import com.devsu.customer_service.application.dto.request.UpdateCustomerRequest;
import com.devsu.customer_service.application.dto.response.CustomerResponse;
import com.devsu.customer_service.application.service.CustomerApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerApplicationService customerApplicationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse createCustomer(
        @Valid
        @RequestBody
        CreateCustomerRequest request
    ) { 
        return customerApplicationService.createCustomer(request);
    }

    @GetMapping("/{customerId}")
    public CustomerResponse getCustomer(
        @PathVariable
        Long customerId
    ) {
        return customerApplicationService.getCustomer(customerId);
    }

    @GetMapping()
    public List<CustomerResponse> getCustomers() {
        return customerApplicationService.getCustomers();
    }

    @PutMapping("/{customerId}")
    public CustomerResponse updateCustomer(
        @PathVariable
        Long customerId,
        @Valid
        @RequestBody
        UpdateCustomerRequest request
    ) {
        return customerApplicationService.updateCustomer(customerId, request);
    }
    
    @DeleteMapping("/{customerId}")
    public CustomerResponse deleteCustomer(
        @PathVariable Long customerId
    ) {
        return customerApplicationService.deleteCustomer(customerId);
    }

}
