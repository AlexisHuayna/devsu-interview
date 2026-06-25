package com.devsu.account_service.infrastructure.messaging.config;

public final class RabbitConstants {
    
    private RabbitConstants(){}

    public static final String CUSTOMER_EXCHANGE = "customer.exchange";

    public static final String ACCOUNT_CUSTOMER_QUEUE = "account.customer.queue";

    public static final String CUSTOMER_CREATED_ROUTING_KEY = "customer.created";

    public static final String CUSTOMER_UPDATED_ROUTING_KEY = "customer.updated";

    public static final String CUSTOMER_DEACTIVATED_ROUTING_KEY = "customer.deactivated";
}
