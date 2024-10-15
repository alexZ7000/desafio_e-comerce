package com.example.comerce.integration.controller;

import com.example.comerce.core.dto.AddressDTO;
import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.dto.UserDTO;
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

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

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
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testGetAllUsers() throws Exception {
        final User user = new User();
        user.setUser_id(UUID.randomUUID());
        user.setEmail("test@example.com");

        when(userService.findAll()).thenReturn(Collections.singletonList(user));

        mockMvc.perform(get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testGetUserById() throws Exception {
        final UUID userId = UUID.randomUUID();
        final User user = new User();
        user.setUser_id(userId);
        user.setEmail("test@example.com");

        when(userService.findById(userId)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testCreateUser() throws Exception {
        final UserDTO userDTO = new UserDTO();
        userDTO.setEmail("test@example.com");

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
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testDeleteUser() throws Exception {
        final UUID userId = UUID.randomUUID();

        doNothing().when(userService).delete(userId);

        mockMvc.perform(delete("/api/users/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}