package com.devsu.customer_service.application.event;

public record CustomerDeactivatedInternalEvent(
    Long customerId
) {
    
}
