package com.devsu.customer_service.infrastructure.messaging.event;

public record CustomerCreatedEvent(
    Long customerId,
    String name,
    boolean active
) {
    
}
