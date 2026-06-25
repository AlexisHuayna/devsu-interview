package com.devsu.account_service.presentation.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsu.account_service.application.dto.request.ReportRequest;
import com.devsu.account_service.application.dto.response.AccountStatementResponse;
import com.devsu.account_service.application.service.ReportApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("api/v1/reports")
@RequiredArgsConstructor
@Validated
public class ReportController {

    private final ReportApplicationService reportApplicationService;

    @GetMapping()
    public List<AccountStatementResponse> generateReport(
        @Valid
        @ModelAttribute
        ReportRequest request
    ){
        return reportApplicationService.generateReport(
            request.customerId(),
            request.startDate(),
            request.endDate()
        );
    }
    
}
