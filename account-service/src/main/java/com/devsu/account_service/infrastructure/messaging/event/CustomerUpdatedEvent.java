package com.devsu.account_service.infrastructure.messaging.event;

public record CustomerUpdatedEvent(
    Long customerId,
    String name,
    boolean active
) {
    
}
