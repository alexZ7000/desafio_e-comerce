package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.UUID;

@Entity
@Table(name = "category")
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "category_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "Category ID não pode ser null")
    private UUID category_id;

    @Column
    @Size(min = 10, max = 255, message = "A descrição da categoria deve ter entre 10 e 255 caracteres")
    private String description;
}
