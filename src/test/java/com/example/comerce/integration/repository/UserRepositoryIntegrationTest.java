package com.example.comerce.integration.repository;

import com.example.comerce.core.entities.User;
import com.example.comerce.core.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveAndFindUser() {
        User user = new User();
        user.setName("Alessandro Lima");
        user.setEmail("alessandro@email.com");
        user.setCpf("12345678901");
        user.setTelephone("12345678901");
        user.setPassword("senha123");

        User savedUser = userRepository.save(user);
        assertNotNull(savedUser.getUser_id());

        Optional<User> foundUser = userRepository.findById(savedUser.getUser_id());
        assertTrue(foundUser.isPresent());
        assertEquals(user.getName(), foundUser.get().getName());
    }
}
