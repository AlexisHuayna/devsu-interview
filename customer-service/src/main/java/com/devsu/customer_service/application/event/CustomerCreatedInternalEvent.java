package com.devsu.customer_service.application.event;

public record CustomerCreatedInternalEvent(
    Long customerId,
    String name,
    boolean active
) {
    
}
