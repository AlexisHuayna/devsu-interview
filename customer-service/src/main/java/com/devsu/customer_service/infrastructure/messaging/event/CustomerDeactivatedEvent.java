package com.devsu.customer_service.infrastructure.messaging.event;

public record CustomerDeactivatedEvent(
    Long customerId
) {
    
}
