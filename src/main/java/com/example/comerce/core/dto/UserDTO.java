package com.example.comerce.core.dto;

import com.example.comerce.core.entities.User;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Getter
@Setter
public final class UserDTO {
    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter no mínimo 3 caracteres e no máximo 100 caracteres")
    @Pattern(
            regexp = "^\\p{L}[\\p{L}\\s]*$",
            message = "Nome deve começar com uma letra, não pode conter números e pode incluir letras acentuadas e espaços."
    )
    private String name;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres")
    private String telephone;

    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "E-mail inválido")
    private String email;

    @Size(min = 6, max = 255, message = "Senha deve ter no mínimo 6 e no máximo 255 caracteres")
    private String password;

    @NotNull(message = "Endereço não pode ser null")
    private AddressDTO address;

    @NotNull(message = "Order não pode ser null")
    private List<OrderDTO> orders;

    public User toEntity() {
        final User user = new User();
        user.setName(this.name);
        user.setTelephone(this.telephone);
        user.setCpf(this.cpf);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address.toEntity());
        user.setOrders(this.orders.stream().map(OrderDTO::toEntity).toList());
        return user;
    }

    public static UserDTO toDTO(User user) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setCpf(user.getCpf());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(AddressDTO.toDTO(user.getAddress()));
        userDTO.setOrders(user.getOrders().stream().map(OrderDTO::toDTO).toList());
        return userDTO;
    }
}
