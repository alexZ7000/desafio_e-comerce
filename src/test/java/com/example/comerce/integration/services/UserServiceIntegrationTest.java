package com.example.comerce.integration.services;

import com.example.comerce.core.entities.User;
import com.example.comerce.core.repository.UserRepository;
import com.example.comerce.core.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional // Garante que cada teste seja executado em uma transação que será revertida
class UserServiceIntegrationTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    private UUID userId;

    @BeforeEach
    public final void setUp() {
        final User user = new User();
        user.setName("Alessandro Lima");
        user.setEmail("alessandro@email.com");
        user.setCpf("12345678901");
        user.setTelephone("12345678901");
        user.setPassword("senha123");

        userId = userRepository.save(user).getUser_id();
    }

    @Test
    public final void testFindById() {
        final Optional<User> foundUser = userService.findById(userId);

        assertTrue(foundUser.isPresent());
        assertEquals(userId, foundUser.get().getUser_id());
    }

    @Test
    public final void testDeleteUser() {
        userService.delete(userId);
        final Optional<User> foundUser = userService.findById(userId);

        assertFalse(foundUser.isPresent());
    }
}
