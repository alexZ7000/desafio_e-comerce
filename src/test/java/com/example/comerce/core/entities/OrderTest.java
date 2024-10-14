package com.example.comerce.core.entities;

import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import com.example.comerce.shared.helpers.validators.errors.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe responsável por testar a Entidade Order e o OrderDTO.
 * */
final class OrderTest {
    private final OrderDTO globalOrder = new OrderDTO();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        globalOrder.setDate(new Date());
        globalOrder.setDiscount(100.00);
        globalOrder.setTotal_price(900.00);
    }

    @Test
    public void testOrder() {
        final Order order = new Order();

        final UUID orderId = UUID.randomUUID();
        order.setOrder_id(orderId);
        order.setDate(new Date());
        order.setDiscount(100.00);
        order.setTotal_price(900.00);

        assertEquals(orderId, order.getOrder_id());

        assertEquals(order.getDate().toString(), order.getDate().toString());
        assertEquals(100.00, order.getDiscount());
        assertEquals(900.00, order.getTotal_price());
    }

    @Test
    public void testInvalidOrderId() {
        final String orderId = !UUIDValidator.validateUUID("orderId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", orderId);
    }

    @Test
    public void testInvalidDate() {
        // Teste 1: Data do pedido nula
        globalOrder.setDate(null);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalOrder);
        assertEquals("Data do pedido não pode ser null", violationMessages.get("date"));

        // Teste 2: Data do pedido no passado
        globalOrder.setDate(new Date(System.currentTimeMillis() - 86400000L)); // 1 dia no passado
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalOrder);
        assertEquals("Data do pedido deve ser no presente ou no futuro", violationMessages.get("date"));
    }

    @Test
    public void testInvalidDiscount() {
        // Teste 1: Desconto negativo
        globalOrder.setDiscount(-50.00);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalOrder);
        assertEquals("O desconto deve ser um valor positivo", violationMessages.get("discount"));
    }

    @Test
    public void testInvalidTotalPrice() {
        // Teste 1: Preço total negativo
        globalOrder.setTotal_price(-900.00);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalOrder);
        assertEquals("O valor total deve ser um valor positivo", violationMessages.get("total_price"));
    }

    @Test
    public void testToEntity() {
        final Order orderEntity = globalOrder.toEntity();

        assertNotNull(orderEntity, "A entidade Order não deve ser null");
        assertEquals(globalOrder.getDate(), orderEntity.getDate());
        assertEquals(globalOrder.getDiscount(), orderEntity.getDiscount());
        assertEquals(globalOrder.getTotal_price(), orderEntity.getTotal_price());
    }

    @Test
    public void testToDTO() {
        final Order orderEntity = globalOrder.toEntity();
        final OrderDTO newOrderDTO = OrderDTO.toDTO(orderEntity);

        assertNotNull(orderEntity, "O OrderDTO não deve ser null");
        assertEquals(globalOrder.getDate(), newOrderDTO.getDate());
        assertEquals(globalOrder.getDiscount(), newOrderDTO.getDiscount());
        assertEquals(globalOrder.getTotal_price(), newOrderDTO.getTotal_price());
    }
}
