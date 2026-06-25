package com.devsu.customer_service.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.devsu.customer_service.application.dto.request.CreateCustomerRequest;
import com.devsu.customer_service.application.port.out.PublishCustomerEventPort;
import com.devsu.customer_service.domain.entity.Gender;
import com.devsu.customer_service.domain.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerRepository customerRepository;

    @MockBean
    private PublishCustomerEventPort publishCustomerEventPort;
    
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    private CreateCustomerRequest createRequest() {
        return new CreateCustomerRequest(
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
    void shouldCreateCustomer() throws Exception {
        CreateCustomerRequest request = createRequest();

        ResultActions response = mockMvc.perform(
            post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                    objectMapper.writeValueAsString(request)
                )
        );

        response.andExpect(
            status().isCreated()
        );
    }
} 
