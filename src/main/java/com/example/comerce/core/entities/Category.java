package com.example.comerce.core.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false, unique = true, updatable = false, length = 36)
    private UUID category_id;

    @Column
    private String description;

    @ManyToMany(mappedBy = "categories")
    private Set<Product> products;
}
