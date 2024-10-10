package com.example.comerce.core.services;

import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID userId) {
        return userRepository.findById(userId);
    }

    public User save(UserDTO userDTO) {
        User user = userDTO.toEntity();
        return userRepository.save(user);
    }

    public void delete(UUID userId) {
        userRepository.deleteById(userId);
    }
}
