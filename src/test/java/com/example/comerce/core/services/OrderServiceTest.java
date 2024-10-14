package com.example.comerce.core.services;

import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.entities.Order;
import com.example.comerce.core.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

final class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    private final OrderDTO orderDTO = new OrderDTO();
    private Order order = new Order();
    private final UUID orderId = UUID.randomUUID();

    private AutoCloseable closeable;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);

        orderDTO.setDate(new Date());
        orderDTO.setDiscount(100.00);
        orderDTO.setTotal_price(900.00);

        order = orderDTO.toEntity();
        order.setOrder_id(orderId);
    }

    @AfterEach
    public void tearDown() {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception e) {
                System.err.println("Erro ao fechar os recursos: " + e.getMessage());
            }
        }
    }

    @Test
    public void testFindById_Success() {
        when(orderService.findById(orderId)).thenReturn(Optional.of(order));
        Optional<Order> foundOrder = orderService.findById(orderId);

        assertTrue(foundOrder.isPresent());
        assertEquals(orderId, foundOrder.get().getOrder_id());
    }

    @Test
    public void testFindById_OrderNotFound() {
        when(orderService.findById(orderId)).thenReturn(Optional.empty());
        Optional<Order> foundOrder = orderService.findById(orderId);

        assertFalse(foundOrder.isPresent());
    }

    @Test
    public void testSaveOrder() {
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order savedOrder = orderService.save(orderDTO);

        assertNotNull(savedOrder, "Não é possível salvar dados vazios");
        assertEquals(order.getOrder_id(), savedOrder.getOrder_id());
    }

    @Test
    public void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(orderId);
        orderService.delete(orderId);

        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    public void testUpdateOrder() {
        OrderDTO updatedOrderDTO = new OrderDTO();

        updatedOrderDTO.setDate(new Date());
        updatedOrderDTO.setDiscount(0);
        updatedOrderDTO.setTotal_price(10);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order updatedOrder = orderService.update(orderId, updatedOrderDTO);

        assertNotNull(orderDTO, "Order atualizado não pode ser nulo");
        assertEquals(orderId, updatedOrder.getOrder_id(), "O ID da Order atualizado deve ser o mesmo que o ID passado");
        assertEquals(updatedOrder.getDate(), updatedOrderDTO.getDate(), "A data da Order deve ser atualizado corretamente");
        assertEquals(updatedOrder.getDiscount(), updatedOrderDTO.getDiscount(), "O desconto da Order deve ser atualizado corretamente");
        assertEquals(updatedOrder.getTotal_price(), updatedOrderDTO.getTotal_price(), "O preço total da Order deve ser atualizado corretamente");

        verify(orderRepository, times(1)).save(any(Order.class));
    }
}
