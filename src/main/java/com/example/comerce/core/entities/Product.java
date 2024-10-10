package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
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
    private UUID product_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int stock_quantity;

    @Column(nullable = false)
    private double cost_price;

    @Column(nullable = false)
    private double sell_price;

    @Column(nullable = false)
    private Date created_at;

    @Column(nullable = false)
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

