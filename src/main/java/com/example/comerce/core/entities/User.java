package com.example.comerce.core.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true, updatable = false, length = 36)
    @NotNull(message = "User ID não pode ser null")
    private UUID user_id;

    @Column(length = 100, nullable = false)
    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter no mínimo 3 caracteres e no máximo 100 caracteres")
    private String name;

    @Column(length = 11, nullable = false, unique = true)
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres")
    private String telephone;

    @Column(length = 11, nullable = false, unique = true)
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @Column(length = 100, nullable = false, unique = true)
    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "E-mail inválido")
    private String email;

    @Column(length = 255, nullable = false)
    private Address address;
}
