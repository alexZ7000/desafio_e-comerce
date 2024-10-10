package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "address")
@Getter
@Setter
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "Address ID não pode ser null")
    private UUID address_id;

    @Column(nullable = false)
    @NotBlank(message = "CEP não pode estar em branco")
    private String postal_code;

    @Column(nullable = false)
    @NotBlank(message = "Rua não pode estar em branco")
    private String street;

    @Column(nullable = false)
    @NotBlank(message = "Número não pode estar em branco")
    private String number;

    @Column(nullable = false)
    @NotBlank(message = "Bairro não pode estar em branco")
    private String complement;

    @Column(nullable = false)
    @NotBlank(message = "Bairro não pode estar em branco")
    private String neighborhood;

    @Column(nullable = false)
    @NotBlank(message = "Cidade não pode estar em branco")
    private String city;

    @Column(nullable = false)
    @NotBlank(message = "Estado não pode estar em branco")
    private String state;
}
