package com.example.comerce.core.dto;

import com.example.comerce.core.entities.Address;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    @NotBlank(message = "CEP não pode estar em branco")
    private String postal_code;

    @NotBlank(message = "Rua não pode estar em branco")
    private String street;

    @NotBlank(message = "Número não pode estar em branco")
    private String number;

    @NotBlank(message = "Bairro não pode estar em branco")
    private String neighborhood;

    @NotBlank(message = "Cidade não pode estar em branco")
    private String city;

    @NotBlank(message = "Estado não pode estar em branco")
    private String state;

    public Address toEntity() {
        Address address = new Address();
        address.setPostal_code(this.postal_code);
        address.setStreet(this.street);
        address.setNumber(this.number);
        address.setNeighborhood(this.neighborhood);
        address.setCity(this.city);
        address.setState(this.state);
        return address;
    }

    public static AddressDTO toDTO(Address address) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setPostal_code(address.getPostal_code());
        addressDTO.setStreet(address.getStreet());
        addressDTO.setNumber(address.getNumber());
        addressDTO.setNeighborhood(address.getNeighborhood());
        addressDTO.setCity(address.getCity());
        addressDTO.setState(address.getState());
        return addressDTO;
    }
}
