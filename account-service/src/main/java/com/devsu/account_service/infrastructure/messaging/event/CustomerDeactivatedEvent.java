package com.devsu.account_service.infrastructure.messaging.event;

public record CustomerDeactivatedEvent(
    Long customerId
) {
    
}
