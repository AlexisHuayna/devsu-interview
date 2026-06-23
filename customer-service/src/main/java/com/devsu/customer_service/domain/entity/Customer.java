package com.devsu.customer_service.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Customer extends Person{

    @Column(nullable = false, length = 255)
    private String password;

    @Column(nullable = false)
    private boolean active;

    public Customer(
        String name,
        Gender gender,
        Integer age,
        String identification,
        String address,
        String phone,
        String password,
        boolean active
    ){
        super(
            name,
            gender,
            age,
            identification,
            address,
            phone
        );
        this.password = password;
        this.active = active;
    }

    public void activate() {
        this.active = true;
    }

    public void deactivate() {
        this.active = false;
    }

    public void update(
        String name,
        Gender gender,
        Integer age,
        String address,
        String phone,
        String password,
        boolean active
    ) {
        updatePersonalInformation(
            name,
            gender,
            age,
            address,
            phone
        );
        this.password = password;
        this.active = active;
    }
    
}
