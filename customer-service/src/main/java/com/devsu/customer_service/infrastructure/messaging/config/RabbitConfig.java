package com.devsu.customer_service.infrastructure.messaging.config;

import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
