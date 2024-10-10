package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "product")
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "Product ID não pode ser null")
    private UUID product_id;

    @Column(nullable = false)
    @NotNull(message = "O nome do produto não pode ser null")
    private String name;

    @Column(nullable = false)
    @Positive(message = "O preço do produto deve ser positivo")
    private double price;

    @ManyToMany
    @JoinTable(
            name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private Set<Category> categories;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;
}

