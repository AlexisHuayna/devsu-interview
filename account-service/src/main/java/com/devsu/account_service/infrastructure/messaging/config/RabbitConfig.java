package com.devsu.account_service.infrastructure.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class RabbitConfig {
    
    @Bean
    public TopicExchange customerExchange() {
        return new TopicExchange(
            RabbitConstants.CUSTOMER_EXCHANGE
        );
    }

    @Bean
    public Queue accountCustomerQueue() {
        return QueueBuilder
                .durable(
                    RabbitConstants.ACCOUNT_CUSTOMER_QUEUE
                ).build();
    }

    @Bean
    public Binding customerBinding(
        Queue accountCustomerQueue,
        TopicExchange customerExchange
    ) {
        return BindingBuilder
                .bind(accountCustomerQueue)
                .to(customerExchange)
                .with("customer.*");
    }

}
