package com.devsu.customer_service.infrastructure.messaging.publisher;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.devsu.customer_service.application.port.out.PublishCustomerEventPort;
import com.devsu.customer_service.infrastructure.messaging.config.RabbitConstants;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerCreatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerDeactivatedEvent;
import com.devsu.customer_service.infrastructure.messaging.event.CustomerUpdatedEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RabbitCustomerEventPublisher implements PublishCustomerEventPort {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishCustomerCreated(CustomerCreatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitConstants.CUSTOMER_EXCHANGE,
            RabbitConstants.CUSTOMER_CREATED_ROUTING_KEY,
            event
        );
    }

    @Override
    public void publishCustomerUpdated(CustomerUpdatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitConstants.CUSTOMER_EXCHANGE,
            RabbitConstants.CUSTOMER_UPDATED_ROUTING_KEY,
            event
        );
    }

    @Override
    public void publishCustomerDeactivated(CustomerDeactivatedEvent event) {
        rabbitTemplate.convertAndSend(
            RabbitConstants.CUSTOMER_EXCHANGE,
            RabbitConstants.CUSTOMER_DEACTIVATED_ROUTING_KEY,
            event
        );
    }
    
}
