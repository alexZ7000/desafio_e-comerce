package com.example.comerce.shared.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    @Autowired
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Desabilitar CSRF, caso você não precise (API REST, por exemplo)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/users").permitAll() // Liberando o endpoint /users
                        .anyRequest().permitAll() // Todas as outras requisições precisam de autenticação
                );

        return http.build(); // Retorna o SecurityFilterChain configurado
    }
}
