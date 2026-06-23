package com.devsu.customer_service.application.dto.response;

import com.devsu.customer_service.domain.entity.Gender;

public record CustomerResponse(
    Long id,
    String name,
    Gender gender,
    Integer age,
    String identification,
    String address,
    String phone,
    Boolean active
) {
    
}
