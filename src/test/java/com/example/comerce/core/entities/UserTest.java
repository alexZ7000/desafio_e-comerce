package com.example.comerce.core.entities;

import com.example.comerce.core.dto.AddressDTO;
import com.example.comerce.core.dto.OrderDTO;
import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import com.example.comerce.shared.helpers.validators.errors.ValidationUtil;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Classe responsável por testar a Entidade User e o UserDTO.
 * */
final class UserTest {
    private final UserDTO globalUser = new UserDTO();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        try (final ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }

        globalUser.setName("Alessandro Lima");
        globalUser.setEmail("alessandro@email.com");
        globalUser.setCpf("12345678901");
        globalUser.setTelephone("12345678901");
        globalUser.setPassword("123456");

        final AddressDTO address = new AddressDTO();
        address.setPostal_code("12345678");
        address.setStreet("Rua Exemplo");
        address.setNumber("123");
        address.setNeighborhood("Bairro Exemplo");
        address.setComplement("Apto 456");
        address.setCity("Cidade Exemplo");
        address.setState("Estado Exemplo");
        globalUser.setAddress(address);

        final OrderDTO order = new OrderDTO();
        order.setDate(new Date());
        order.setDiscount(10.0);
        order.setTotal_price(100.0);
        globalUser.setOrders(List.of(order));
    }

    @Test
    public void testUser() {
        final User user = new User();

        final UUID userId = UUID.randomUUID();
        user.setUser_id(userId);
        user.setName("Alessandro Lima");
        user.setEmail("alessandro@email.com");
        user.setCpf("12345678901");
        user.setTelephone("12345678901");
        user.setPassword("123456");

        final Address address = new Address();
        address.setPostal_code("12345678");
        address.setStreet("Rua Exemplo");
        address.setNumber("123");
        address.setNeighborhood("Bairro Exemplo");
        address.setComplement("Apto 456");
        address.setCity("Cidade Exemplo");
        address.setState("Estado Exemplo");
        user.setAddress(address);

        final Order order = new Order();
        order.setDate(new Date());
        order.setDiscount(10.0);
        order.setTotal_price(100.0);
        user.setOrders(List.of(order));

        assertEquals(userId, user.getUser_id());
        assertEquals("Alessandro Lima", user.getName());
        assertEquals("alessandro@email.com" , user.getEmail());
        assertEquals("12345678901", user.getCpf());
        assertEquals("12345678901", user.getTelephone());
        assertEquals("123456", user.getPassword());

        assertEquals("12345678", user.getAddress().getPostal_code());
        assertEquals("Rua Exemplo", user.getAddress().getStreet());
        assertEquals("123", user.getAddress().getNumber());
        assertEquals("Bairro Exemplo", user.getAddress().getNeighborhood());
        assertEquals("Apto 456", user.getAddress().getComplement());
        assertEquals("Cidade Exemplo", user.getAddress().getCity());
        assertEquals("Estado Exemplo", user.getAddress().getState());

        assertEquals(1, user.getOrders().size());
        assertEquals(10.0, user.getOrders().getFirst().getDiscount());
        assertEquals(100.0, user.getOrders().getFirst().getTotal_price());
    }

    @Test
    public void testInvalidUserId() {
        final String userId = !UUIDValidator
                .validateUUID("userId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", userId);
    }

    @Test
    public void testInvalidName() {
        // Teste 1: Nome com menos de 3 caracteres
        globalUser.setName("ab");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Nome deve ter no mínimo 3 caracteres e no máximo 100 caracteres", violationMessages.get("name"));

        // Teste 2: Nome com caracteres inválidos
        globalUser.setName("123abc");
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Nome deve começar com uma letra, não pode conter números e pode incluir letras acentuadas e espaços.", violationMessages.get("name"));
    }

    @Test
    public void testInvalidEmail() {
        // Teste 1: Email em branco
        globalUser.setEmail("");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Email não pode estar em branco", violationMessages.get("email"));

        // Teste 2: Email inválido
        globalUser.setEmail("alessandroemail.com");
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("E-mail inválido", violationMessages.get("email"));
    }

    @Test
    public void testInvalidCpf() {
        // Teste 1: CPF em branco
        globalUser.setCpf("           ");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("CPF não pode estar em branco", violationMessages.get("cpf"));

        // Teste 2: CPF inválido
        globalUser.setCpf("1234567890");
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("CPF deve ter 11 caracteres", violationMessages.get("cpf"));
    }

    @Test
    public void testInvalidTelephone() {
        // Teste 1: Telefone em branco
        globalUser.setTelephone("           ");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Telefone não pode estar em branco", violationMessages.get("telephone"));

        // Teste 2: Telefone inválido
        globalUser.setTelephone("123456789");
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Telefone deve ter 11 caracteres", violationMessages.get("telephone"));
    }

    @Test
    public void testInvalidPassword() {
        // Teste 1: Senha em branco
        globalUser.setPassword("      ");
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Senha não pode estar em branco", violationMessages.get("password"));

        // Teste 2: Senha com menos de 6 caracteres
        globalUser.setPassword("12345");
        violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Senha deve ter no mínimo 6 e no máximo 255 caracteres", violationMessages.get("password"));
    }

    @Test
    public void testInvalidAddress() {
        // Teste 1: Endereço em branco
        globalUser.setAddress(null);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Endereço não pode ser null", violationMessages.get("address"));
    }

    @Test
    public void testInvalidOrder() {
        // Teste 1: Pedido em branco
        globalUser.setOrders(null);
        Map<String, String> violationMessages = ValidationUtil.validateAndGetViolations(validator, globalUser);
        assertEquals("Order não pode ser null", violationMessages.get("orders"));
    }

    @Test
    public void testToEntity() {
        final User userEntity = globalUser.toEntity();

        assertNotNull(userEntity, "A entidade User não deve ser null.");
        assertEquals(globalUser.getName(), userEntity.getName(), "O nome deve ser igual na entidade.");
        assertEquals(globalUser.getEmail(), userEntity.getEmail(), "O e-mail deve ser igual na entidade.");
        assertEquals(globalUser.getCpf(), userEntity.getCpf(), "O CPF deve ser igual na entidade.");
        assertEquals(globalUser.getTelephone(), userEntity.getTelephone(), "O telefone deve ser igual na entidade.");
        assertEquals(globalUser.getPassword(), userEntity.getPassword(), "A senha deve ser igual na entidade.");
    }

    @Test
    public void testToDTO() {
        final User userEntity = globalUser.toEntity();
        final UserDTO newUserDTO = UserDTO.toDTO(userEntity);

        assertNotNull(newUserDTO, "O UserDTO não deve ser null.");
        assertEquals(userEntity.getName(), newUserDTO.getName(), "O nome deve ser igual no DTO.");
        assertEquals(userEntity.getEmail(), newUserDTO.getEmail(), "O e-mail deve ser igual no DTO.");
        assertEquals(userEntity.getCpf(), newUserDTO.getCpf(), "O CPF deve ser igual no DTO.");
        assertEquals(userEntity.getTelephone(), newUserDTO.getTelephone(), "O telefone deve ser igual no DTO.");
    }
}
