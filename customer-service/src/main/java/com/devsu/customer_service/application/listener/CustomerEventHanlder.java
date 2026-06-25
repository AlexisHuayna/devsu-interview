package com.devsu.customer_service.application.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.devsu.customer_service.application.event.CustomerCreatedInternalEvent;
import com.devsu.customer_service.application.event.CustomerDeactivatedInternalEvent;
import com.devsu.customer_service.application.event.CustomerUpdatedInternalEvent;
import com.devsu.customer_service.application.port.out.PublishCustomerEventPort;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerCreatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerDeactivatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomerEventHanlder {
    private final PublishCustomerEventPort publishCustomerEventPort;

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(
        CustomerCreatedInternalEvent event
    ) {
        publishCustomerEventPort.publishCustomerCreated(
            new CustomerCreatedEvent(
                event.customerId(),
                event.name(),
                event.active()
            )
        );
    }

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(
        CustomerUpdatedInternalEvent event
    ) {
        publishCustomerEventPort.publishCustomerUpdated(
            new CustomerUpdatedEvent(
                event.customerId(),
                event.name(),
                event.active()
            )
        );
    }

    @TransactionalEventListener(
        phase = TransactionPhase.AFTER_COMMIT
    )
    public void handle(
        CustomerDeactivatedInternalEvent event
    ) {
        publishCustomerEventPort.publishCustomerDeactivated(
            new CustomerDeactivatedEvent(
                event.customerId()
            )
        );
    }
}
