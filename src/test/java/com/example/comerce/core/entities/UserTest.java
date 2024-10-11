package com.example.comerce.core.entities;

import com.example.comerce.shared.helpers.validators.entities.UUIDValidator;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class UserTest {
    private final User globalUser = new User();
    private Validator validator;

    @BeforeEach
    public void setUp() {
        final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = (Validator) factory.getValidator();
        final UUID userId = UUID.randomUUID();
        globalUser.setUser_id(userId);
        globalUser.setName("Alessandro Lima");
        globalUser.setEmail("alessandro@email.com");
        globalUser.setCpf("12345678901");
        globalUser.setTelephone("12345678901");
        globalUser.setPassword("123456");
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

        assertEquals(userId, user.getUser_id());
        assertEquals("Alessandro Lima", user.getName());
        assertEquals("alessandro@email.com" , user.getEmail());
        assertEquals("12345678901", user.getCpf());
        assertEquals("12345678901", user.getTelephone());
        assertEquals("123456", user.getPassword());
    }

    @Test
    public void testInvalidUserId() {
        String userId = !UUIDValidator
                .validateUUID("userId", "123e4567-e89b-12d3-a456-42661417400")
                ? "UUID inválido" : "UUID válido";
        assertEquals("UUID inválido", userId);
    }
}
