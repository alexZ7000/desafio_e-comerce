package com.example.comerce.integration.controller;

import com.example.comerce.core.dto.AddressDTO;
import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.core.entities.LoginRequest;
import com.example.comerce.core.entities.LoginResponse;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
final class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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
    @WithMockUser(username = "alessandro.lima@example.com")
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

    @Test
    @WithMockUser(username = "alessandro.lima@example.com")
    public void testGetAllUsers() throws Exception {
        final User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setName("Test User");

        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("test@example.com"))
                .andExpect(jsonPath("$[0].name").value("Test User"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com")
    public void testGetUserById() throws Exception {
        final UUID userId = UUID.randomUUID();
        final User user = new User();
        user.setUser_id(userId);
        user.setEmail("test@example.com");
        user.setName("Test User");

        when(userService.findById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com")
    public void testCreateUser() throws Exception {
        final UserDTO userDTO = new UserDTO();
        userDTO.setName("Test User");
        userDTO.setEmail("test@example.com");
        userDTO.setCpf("12345678901");
        userDTO.setTelephone("12345678901");
        userDTO.setPassword("password");

        final AddressDTO address = new AddressDTO();
        address.setPostal_code("12345678");
        address.setStreet("Rua Exemplo");
        address.setNumber("123");
        address.setNeighborhood("Bairro Exemplo");
        address.setComplement("Apto 456");
        address.setCity("Cidade Exemplo");
        address.setState("Estado Exemplo");
        userDTO.setAddress(address);

        final OrderDTO order = new OrderDTO();
        order.setDate(new Date());
        order.setDiscount(10.0);
        order.setTotal_price(100.0);
        userDTO.setOrders(List.of(order));

        final User user = userDTO.toEntity();
        user.setUser_id(UUID.randomUUID());

        when(userService.save(any(UserDTO.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"))
                .andExpect(jsonPath("$.name").value("Test User"));
    }

    @Test
    @WithMockUser(username = "alessandro.lima@example.com")
    public void testDeleteUser() throws Exception {
        final UUID userId = UUID.randomUUID();

        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}
