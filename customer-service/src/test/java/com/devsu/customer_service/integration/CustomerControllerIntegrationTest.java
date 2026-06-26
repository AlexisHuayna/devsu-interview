package com.devsu.customer_service.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;

import org.mockito.ArgumentCaptor;

import com.devsu.customer_service.infrastructure.messaging.event.CustomerCreatedEvent;
import com.devsu.customer_service.domain.entity.Customer;

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

        response.andExpect(
            jsonPath("$.name")
                .value(request.name())
        );

        assertTrue(
            customerRepository.existsByIdentification(
                request.identification()
            )
        );

        assertEquals(
            1,
            customerRepository.findAll().size()
        );
        
        Customer customer =
            customerRepository.findAll().get(0);
        
        assertAll(
            () -> assertEquals(request.name(), customer.getName()),
            () -> assertEquals(request.identification(), customer.getIdentification()),
            () -> assertEquals(request.phone(), customer.getPhone()),
            () -> assertEquals(request.address(), customer.getAddress()),
            () -> assertEquals(request.active(), customer.isActive())
        );

        ArgumentCaptor<CustomerCreatedEvent> captor =
        ArgumentCaptor.forClass(CustomerCreatedEvent.class);

verify(
    publishCustomerEventPort,
    times(1)
).publishCustomerCreated(captor.capture());

CustomerCreatedEvent event =
        captor.getValue();

assertAll(
    () -> assertNotNull(event.customerId()),
    () -> assertEquals(request.name(), event.name()),
    () -> assertEquals(request.active(), event.active())
);
    }

    @Test
void shouldReturnConflictWhenIdentificationAlreadyExists() throws Exception {

    // Arrange

    CreateCustomerRequest request = createRequest();

    mockMvc.perform(
            post("/api/v1/customers")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(
                            objectMapper.writeValueAsString(request)
                    )
    );

    // Act

    ResultActions response =
            mockMvc.perform(
                    post("/api/v1/customers")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(
                                    objectMapper.writeValueAsString(request)
                            )
            );

    // Assert HTTP

    response.andExpect(status().isConflict());

    response.andExpect(
            jsonPath("$.status")
                    .value(409)
    );

    response.andExpect(
            jsonPath("$.error")
                    .value("Customer Already Exists")
    );

    response.andExpect(
            jsonPath("$.message")
                    .exists()
    );

    assertEquals(
            1,
            customerRepository.findAll().size()
    );

    verify(
            publishCustomerEventPort,
            times(1)
    ).publishCustomerCreated(any());
}
} 
