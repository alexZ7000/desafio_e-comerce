package com.example.comerce.core.controller;

import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.core.entities.LoginRequest;
import com.example.comerce.core.entities.LoginResponse;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public final class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        final List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable final UUID id) {
        final Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody final UserDTO userDTO) {
        final User savedUser = userService.save(userDTO);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody final LoginRequest request) throws Exception {
        return userService.login(request.getEmail(), request.getPassword());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable final UUID id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

