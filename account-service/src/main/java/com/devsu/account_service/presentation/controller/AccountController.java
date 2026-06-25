package com.devsu.account_service.presentation.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.account_service.application.dto.request.CreateAccountRequest;
import com.devsu.account_service.application.dto.request.UpdateAccountRequest;
import com.devsu.account_service.application.dto.response.AccountResponse;
import com.devsu.account_service.application.service.AccountApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("api/v1/accounts")
@RequiredArgsConstructor
@Validated
public class AccountController {
    
    private final AccountApplicationService accountApplicationService;

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(
        @Valid
        @RequestBody
        CreateAccountRequest request
    ) {
        return accountApplicationService.createAccount(request);
    }

    @GetMapping()
    public List<AccountResponse> getAccounts() {
        return accountApplicationService.getAccounts();
    }

    @GetMapping("/{accountId}")
    public AccountResponse getAccount(@PathVariable Long accountId) {
        return accountApplicationService.getAccountById(accountId);
    }
    
    @PutMapping("/{accountId}")
    public AccountResponse putMethodName(
        @PathVariable
        Long accountId,
        @Valid
        @RequestBody
        UpdateAccountRequest request
    ) {
        return accountApplicationService.updateAccount(accountId, request);
    }
    
}
