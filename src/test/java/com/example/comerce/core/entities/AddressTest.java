package com.example.comerce.core.entities;

import com.example.comerce.core.dto.AddressDTO;
import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import com.example.comerce.shared.helpers.validators.errors.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class AddressTest {
    private final AddressDTO globalAddress = new AddressDTO();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        globalAddress.setPostal_code("12345-678");
        globalAddress.setStreet("Rua das Flores");
        globalAddress.setNumber("100");
        globalAddress.setNeighborhood("Centro");
        globalAddress.setComplement("Apto 101");
        globalAddress.setCity("São Paulo");
        globalAddress.setState("SP");
    }

    @Test
    public void testAddress() {
        final Address address = new Address();

        final UUID addressId = UUID.randomUUID();
        address.setAddress_id(addressId);
        address.setPostal_code("12345-678");
        address.setStreet("Rua das Flores");
        address.setNumber("100");
        address.setNeighborhood("Centro");
        address.setComplement("Apto 101");
        address.setCity("São Paulo");
        address.setState("SP");

        assertEquals(addressId, address.getAddress_id());
        assertEquals("12345-678", address.getPostal_code());
        assertEquals("Rua das Flores", address.getStreet());
        assertEquals("100", address.getNumber());
        assertEquals("Centro", address.getNeighborhood());
        assertEquals("Apto 101", address.getComplement());
        assertEquals("São Paulo", address.getCity());
        assertEquals("SP", address.getState());
    }

    @Test
    public void testInvalidAddressId() {
        final String addressId = !UUIDValidator.validateUUID("addressId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", addressId);
    }

    @Test
    public void testInvalidPostalCode() {
        // Teste 1: CEP em branco
        globalAddress.setPostal_code("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("CEP não pode estar em branco", violationMessages.get("postal_code"));
    }

    @Test
    public void testInvalidStreet() {
        // Teste 1: Rua em branco
        globalAddress.setStreet("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Rua não pode estar em branco", violationMessages.get("street"));
    }

    @Test
    public void testInvalidNumber() {
        // Teste 1: Número em branco
        globalAddress.setNumber("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Número não pode estar em branco", violationMessages.get("number"));
    }

    @Test
    public void testInvalidNeighborhood() {
        // Teste 1: Bairro em branco
        globalAddress.setNeighborhood("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Bairro não pode estar em branco", violationMessages.get("neighborhood"));
    }

    @Test
    public void testInvalidComplement() {
        // Teste 1: Complemento em branco
        globalAddress.setComplement("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Complemento não pode estar em branco", violationMessages.get("complement"));
    }

    @Test
    public void testInvalidCity() {
        // Teste 1: Cidade em branco
        globalAddress.setCity("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Cidade não pode estar em branco", violationMessages.get("city"));
    }

    @Test
    public void testInvalidState() {
        // Teste 1: Estado em branco
        globalAddress.setState("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalAddress);
        assertEquals("Estado não pode estar em branco", violationMessages.get("state"));
    }
}
