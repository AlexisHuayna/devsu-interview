package com.devsu.customer_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "customers",
    indexes = {
        @Index(
            name = "idx_customer_active",
            columnList = "active"
        )
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Customer extends Person{
    
    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean active;
}
