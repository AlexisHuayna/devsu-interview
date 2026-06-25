package com.devsu.account_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "customer_snapshots"
)
@Getter
@NoArgsConstructor
public class CustomerSnapshot extends BaseEntity {
    
    @Column(
        nullable = false,
        unique = true
    )
    private Long customerId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private boolean active;

    public CustomerSnapshot(
        Long customerId,
        String name,
        boolean active
    ) {
        this.customerId = customerId;
        this.name = name;
        this.active = active;
    }

    public void update(
        String name,
        boolean active
    ) {
        this.name = name;
        this.active = active;
    }

    public void deactivate() {
        this.active = false;
    }
}
