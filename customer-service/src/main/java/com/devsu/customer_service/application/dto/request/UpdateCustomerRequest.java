package com.devsu.customer_service.application.dto.request;

import com.devsu.customer_service.domain.entity.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(

    @NotBlank(message = "Customer name is required")
    @Size(max = 100, min = 3)
    String name,

    @NotNull(message = "Gender is required")
    Gender gender,

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be greater than zero")
    Integer age,

    @NotBlank(message = "Address is required")
    @Size(max = 200, min = 7)
    String address,

    @NotBlank(message = "Phone is required")
    @Size(max = 20, min = 7)
    String phone,

    @NotBlank(message = "Password is required")
    String password,

    @NotNull(message = "Customer status is required")
    boolean active
) {
    
}
