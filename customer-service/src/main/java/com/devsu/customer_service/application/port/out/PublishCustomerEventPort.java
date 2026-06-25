package com.devsu.customer_service.application.port.out;

import com.devsu.customer_service.infrastructure.messaging.event.CustomerCreatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerDeactivatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerUpdatedEvent;

public interface PublishCustomerEventPort {
    void publishCustomerCreated(
        CustomerCreatedEvent event
    );

    void publishCustomerUpdated(
        CustomerUpdatedEvent event
    );

    void publishCustomerDeactivated(
        CustomerDeactivatedEvent event
    );
}
