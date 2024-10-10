package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "Order ID não pode ser null")
    private UUID order_id;

    @Column(nullable = false)
    @NotNull(message = "Data do pedido não pode ser null")
    @FutureOrPresent(message = "Data do pedido deve ser no presente ou no futuro")
    private Date date;

    @Column
    @Positive(message = "O desconto deve ser um valor positivo")
    private double discount;

    @Column(nullable = false)
    @Positive(message = "O valor total deve ser um valor positivo")
    private double total_price;
}
