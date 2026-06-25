package com.devsu.customer_service.domain.entity;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class CustomerTest {
    
    private Customer createCustomer() {
        return new Customer(
            "Alexis Huayna",
            Gender.MALE,
            28,
            "12345678",
            "Arequipa",
            "999888777",
            "password123",
            true
        );
    }

    @Test
    void shouldDeactivateCustomer() {
        // Arrange
        Customer customer = createCustomer();

        // Act
        customer.deactivate();

        // Assert
        assertFalse(customer.isActive());
    }

    @Test
    void shouldActivateCustomer() {
        Customer customer = createCustomer();
        customer.deactivate();

        customer.activate();

        assertTrue(customer.isActive());
    }

    @Test
    void shouldUpdateCustomerInformation() {
        Customer customer = createCustomer();

        customer.update(
            "Fernanda Pillco",
            Gender.FEMALE, 
            30,
            "Lima", 
            "958989890",
            "otropassword123",
            false
        );

        assertAll(
            () -> assertEquals("Fernanda Pillco", customer.getName()),
            () -> assertEquals(Gender.FEMALE, customer.getGender()),
            () -> assertEquals(30, customer.getAge()),
            () -> assertEquals("Lima", customer.getAddress()),
            () -> assertEquals("958989890", customer.getPhone()),
            () -> assertEquals("otropassword123", customer.getPassword()),
            () -> assertEquals("12345678", customer.getIdentification()),
            () -> assertFalse(customer.isActive())
        );

    }
}
