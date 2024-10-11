package com.example.comerce.core.services;

import com.example.comerce.core.dto.UserDTO;
import com.example.comerce.core.entities.LoginResponse;
import com.example.comerce.core.entities.User;
import com.example.comerce.core.repository.UserRepository;
import com.example.comerce.shared.security.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public final class UserService {

    private final UserRepository userRepository;
    private final JWTService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public UserService(final UserRepository userRepository,
                       final JWTService jwtService,
                       final PasswordEncoder passwordEncoder,
                       final AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(final UUID userId) {
        return userRepository.findById(userId);
    }

    public User save(final UserDTO userDTO) {
        final User user = userDTO.toEntity();
        final String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public void delete(final UUID userId) {
        userRepository.deleteById(userId);
    }

    public LoginResponse login(final String email, final String senha) throws Exception {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new Exception("User not found"));

        final Authentication autenticacao = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, senha));

        SecurityContextHolder.getContext().setAuthentication(autenticacao);
        final String token = "Bearer " + jwtService.generateToken(email);

        return new LoginResponse(token, user);
    }


}
