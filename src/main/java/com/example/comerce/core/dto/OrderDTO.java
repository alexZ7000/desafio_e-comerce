package com.example.comerce.core.dto;

import com.example.comerce.core.entities.Order;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public final class OrderDTO {

    @NotNull(message = "Data do pedido não pode ser null")
    @FutureOrPresent(message = "Data do pedido deve ser no presente ou no futuro")
    private Date date;

    @Positive(message = "O desconto deve ser um valor positivo")
    private double discount;

    @Positive(message = "O valor total deve ser um valor positivo")
    private double total_price;

    public Order toEntity() {
        final Order order = new Order();
        order.setDate(this.date);
        order.setDiscount(this.discount);
        order.setTotal_price(this.total_price);
        return order;
    }

    public static OrderDTO toDTO(Order order) {
        final OrderDTO orderDTO = new OrderDTO();
        orderDTO.setDate(order.getDate());
        orderDTO.setDiscount(order.getDiscount());
        orderDTO.setTotal_price(order.getTotal_price());
        return orderDTO;
    }
}
