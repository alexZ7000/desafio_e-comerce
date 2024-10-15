package com.example.comerce.integration.controller;

import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.entities.Order;
import com.example.comerce.core.services.OrderService;
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

import java.util.Collections;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
final class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

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
    public void testGetAllOrders() throws Exception {
        final Order order = new Order();
        order.setOrder_id(UUID.randomUUID());
        order.setDate(new Date());
        order.setTotal_price(100.0);

        when(orderService.findAll()).thenReturn(Collections.singletonList(order));

        mockMvc.perform(get("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].total_price").value(100.0));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testGetOrderById() throws Exception {
        final UUID orderId = UUID.randomUUID();
        final Order order = new Order();
        order.setOrder_id(orderId);
        order.setDate(new Date());
        order.setTotal_price(100.0);

        when(orderService.findById(orderId)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_price").value(100.0));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testCreateOrder() throws Exception {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDate(new Date());
        orderDTO.setTotal_price(100.0);

        final Order order = orderDTO.toEntity();
        order.setOrder_id(UUID.randomUUID());

        when(orderService.save(any(OrderDTO.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderDTO)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.total_price").value(100.0));
    }

    @Test
    @WithMockUser(username = "test@example.com", roles = {"USER"})
    public void testDeleteOrder() throws Exception {
        final UUID orderId = UUID.randomUUID();

        doNothing().when(orderService).delete(orderId);

        mockMvc.perform(delete("/api/orders/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}