package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public final class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "item_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "Item ID não pode ser null")
    private UUID item_id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(nullable = false)
    @Positive(message = "A quantidade deve ser positiva")
    private int quantity;

    @Column(nullable = false)
    @Positive(message = "O preço do item deve ser positivo")
    private double price;
}

