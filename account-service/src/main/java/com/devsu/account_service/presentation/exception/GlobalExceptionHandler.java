package com.devsu.account_service.presentation.exception;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.devsu.account_service.domain.exception.AccountNotFoundException;
import com.devsu.account_service.domain.exception.AccountNumberAlreadyExistsException;
import com.devsu.account_service.domain.exception.AccountNumberNotFoundException;
import com.devsu.account_service.domain.exception.CustomerInactiveException;
import com.devsu.account_service.domain.exception.CustomerNotFoundException;
import com.devsu.account_service.domain.exception.InsufficientBalanceException;

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

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNotFoundException(
        AccountNotFoundException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "Account Not Found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity<ApiError> handleInsufficientBalanceException(
        InsufficientBalanceException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Insufficient Balance",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(AccountNumberAlreadyExistsException.class)
    public ResponseEntity<ApiError> handleAccountNumberAlreadyExistsException(
        AccountNumberAlreadyExistsException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Account already exits",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(AccountNumberNotFoundException.class)
    public ResponseEntity<ApiError> handleAccountNumberNotFoundException(
        AccountNumberNotFoundException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.NOT_FOUND.value(),
            "AccountNumber not found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(CustomerInactiveException.class)
    public ResponseEntity<ApiError> handleCustomerInactiveException(
        CustomerInactiveException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Customer inactive",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ApiError> handleCustomerNotFoundException(
        CustomerNotFoundException ex,
        HttpServletRequest request
    ){
        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.CONFLICT.value(),
            "Customer not found",
            ex.getMessage(),
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiError> handleMissingRequestParameter(
    MissingServletRequestParameterException ex,
    HttpServletRequest request
    ) {

        ApiError error = new ApiError(
            LocalDateTime.now(),
            HttpStatus.BAD_REQUEST.value(),
            "Validation Error",
            ex.getParameterName() + " is required",
            request.getRequestURI(),
            List.of()
        );

        return ResponseEntity.badRequest().body(error);
    }
    
}
