package com.devsu.account_service.domain.entity;

import jakarta.persistence.Column;

public class CustomerSnapshot extends BaseEntity {
    
    @Column(
        nullable = false,
        unique = true
    )
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Boolean active;
}
