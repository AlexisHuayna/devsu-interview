package com.devsu.account_service.infrastructure.messaging.event;

public record CustomerCreatedEvent(
    Long customerId,
    String name,
    boolean active
) {
    
}
