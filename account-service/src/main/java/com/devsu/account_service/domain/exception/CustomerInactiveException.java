package com.devsu.account_service.domain.exception;

public class CustomerInactiveException extends RuntimeException{

    public CustomerInactiveException(
        Long customerId
    ){
        super(
             "Customer "
            + customerId
            + " is inactive."
        );
    }
    
}
