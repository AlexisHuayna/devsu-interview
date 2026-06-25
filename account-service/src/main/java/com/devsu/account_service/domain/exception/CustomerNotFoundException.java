package com.devsu.account_service.domain.exception;

public class CustomerNotFoundException extends RuntimeException {
    
    public CustomerNotFoundException(
        Long customerId
    ) {
        super(
            "Customer "
            + customerId
            + " was not found."
        );
    }
}
