package com.devsu.customer_service.application.event;

public record CustomerUpdatedInternalEvent(
    Long customerId,
    String name,
    boolean active
) {}