package com.devsu.customer_service.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsu.customer_service.domain.exception.CustomerAlreadyExistsException;
import com.devsu.customer_service.domain.exception.CustomerNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
        MethodArgumentNotValidException ex,
        HttpServletRequest request
    ) {
        List<String> details = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(FieldError::getDefaultMessage)
            .toList();

        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            "RequestValidation failed",
            request.getRequestURI(),
            details
        );

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiError> hanldeCustomerNotFoundException(
        CustomerNotFoundException ex,
        HttpServletRequest request
    ) {
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Customer Not Found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomerAlreadyExistsException.class)
    public ResponseEntity<ApiError> hanldeCustomerAlreadyExistsException(
        CustomerAlreadyExistsException ex,
        HttpServletRequest request
    ) {
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Customer Already Exists",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }
}
