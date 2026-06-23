package com.devsu.customer_service.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
    LocalDateTime timestamp,
    Integer status,
    String error,
    String message,
    String path,
    List<String> details
) {
}