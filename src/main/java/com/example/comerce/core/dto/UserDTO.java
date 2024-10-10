package com.example.comerce.core.dto;

import com.example.comerce.core.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class UserDTO {
    @NotBlank(message = "Nome não pode estar em branco")
    @Size(min = 3, max = 100, message = "Nome deve ter no mínimo 3 e no máximo 100 caracteres")
    private String name;

    @NotBlank(message = "Telefone não pode estar em branco")
    @Size(min = 11, max = 11, message = "Telefone deve ter 11 caracteres")
    private String telephone;

    @NotBlank(message = "CPF não pode estar em branco")
    @Size(min = 11, max = 11, message = "CPF deve ter 11 caracteres")
    private String cpf;

    @NotBlank(message = "Email não pode estar em branco")
    @Email(message = "E-mail inválido")
    private String email;

    @NotBlank(message = "Senha não pode estar em branco")
    @Size(min = 6, max = 255, message = "Senha deve ter no mínimo 6 e no máximo 255 caracteres")
    private String password;

    @NotNull(message = "Endereço não pode ser null")
    private AddressDTO address;

    public User toEntity() {
        final User user = new User();
        user.setName(this.name);
        user.setTelephone(this.telephone);
        user.setCpf(this.cpf);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setAddress(this.address.toEntity());
        return user;
    }

    public static UserDTO toDTO(User user) {
        final UserDTO userDTO = new UserDTO();
        userDTO.setName(user.getName());
        userDTO.setTelephone(user.getTelephone());
        userDTO.setCpf(user.getCpf());
        userDTO.setEmail(user.getEmail());
        userDTO.setAddress(AddressDTO.toDTO(user.getAddress()));
        return userDTO;
    }
}
