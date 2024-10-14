package com.example.comerce.integration.controller;

import com.example.comerce.core.controller.UserController;
import com.example.comerce.core.entities.LoginRequest;
import com.example.comerce.core.entities.LoginResponse;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
final class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    private ObjectMapper objectMapper;

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }

    @AfterEach
    public void tearDown() {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception e) {
                throw new RuntimeException("Error closing resources", e);
            }
        }
    }

    @Test
    public void testLogin() throws Exception {
        final User user = new User();
        user.setUser_id(UUID.fromString("81b9aef2-c202-4a44-9a75-d3a11afdb714"));
        user.setEmail("alessandro.lima@example.com");
        user.setName("Alessandro Lima");

        // Token fictício
        final LoginResponse loginResponse = new LoginResponse("Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3Mjg5MjU5ODk5ODEsInVzZXJuYW1lIjoiYWxlc3NhbmRyby5saW1hQGV4YW1wbGUuY29tIn0=.XLeeEDpm0YGa8GAgkTU1X6KRVfnuxnMASACONjMGzoI=", user);

        final LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("alessandro.lima@example.com");
        loginRequest.setPassword("testando");

        when(userService.login(any(String.class), any(String.class))).thenReturn(loginResponse);

        mockMvc.perform(post("/api/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.token").value(containsString("Bearer ")))
                .andExpect(jsonPath("$.user.email").value("alessandro.lima@example.com"));
    }
}
