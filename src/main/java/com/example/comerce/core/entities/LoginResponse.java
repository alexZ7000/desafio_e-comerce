package com.example.comerce.core.entities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    private String token;
    private User user;

    public LoginResponse() {}

    public LoginResponse(final String token, final User user) {
        this.token = token;
        this.user = user;
    }
}
