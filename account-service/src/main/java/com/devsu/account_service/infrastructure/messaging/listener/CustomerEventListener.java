package com.devsu.account_service.infrastructure.messaging.listener;

import java.nio.charset.StandardCharsets;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.devsu.account_service.application.service.CustomerSnapshotApplicationService;
import com.devsu.account_service.infrastructure.messaging.config.RabbitConstants;
import com.devsu.account_service.infrastructure.messaging.event.CustomerCreatedEvent;
import com.devsu.account_service.infrastructure.messaging.event.CustomerDeactivatedEvent;
import com.devsu.account_service.infrastructure.messaging.event.CustomerUpdatedEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@Component

@RequiredArgsConstructor
public class CustomerEventListener {

    private final CustomerSnapshotApplicationService customerSnapshotApplicationService;
    private final ObjectMapper objectMapper;
    
    @RabbitListener(
        queues = RabbitConstants.ACCOUNT_CUSTOMER_QUEUE
    )
    public void hanlde(Message message) throws JsonProcessingException {
        String routingKey = message.getMessageProperties().getReceivedRoutingKey();
        String body = 
            new String(
                message.getBody(),
                StandardCharsets.UTF_8
            );
        
        switch (routingKey) {
            case RabbitConstants.CUSTOMER_CREATED_ROUTING_KEY:
                CustomerCreatedEvent customerCreatedEvent =
                    objectMapper.readValue(body, CustomerCreatedEvent.class);

                customerSnapshotApplicationService.createSnapshot(
                    customerCreatedEvent.customerId(),
                    customerCreatedEvent.name(),
                    customerCreatedEvent.active()
                );
                break;
            
            case RabbitConstants.CUSTOMER_UPDATED_ROUTING_KEY:
                CustomerUpdatedEvent customerUpdatedEvent =
                    objectMapper.readValue(body, CustomerUpdatedEvent.class);

                customerSnapshotApplicationService.updateSnapshot(
                    customerUpdatedEvent.customerId(),
                    customerUpdatedEvent.name(),
                    customerUpdatedEvent.active()
                );
                break;
            
            case RabbitConstants.CUSTOMER_DEACTIVATED_ROUTING_KEY:
                CustomerDeactivatedEvent customerDeactivatedEvent =
                    objectMapper.readValue(body, CustomerDeactivatedEvent.class);

                customerSnapshotApplicationService.deactivateSnapshot(
                    customerDeactivatedEvent.customerId()
                );
                break;

            default:
                break;
        }
    }
}
