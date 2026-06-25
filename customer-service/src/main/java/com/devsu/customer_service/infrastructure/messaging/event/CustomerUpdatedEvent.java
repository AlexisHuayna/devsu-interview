package com.devsu.customer_service.infrastructure.messaging.event;

public record CustomerUpdatedEvent(
    Long customerId,
    String name,
    boolean active
) {
    
}
