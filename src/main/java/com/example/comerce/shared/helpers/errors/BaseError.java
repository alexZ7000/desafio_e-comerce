package com.example.comerce.shared.helpers.errors;

import lombok.Getter;

@Getter
class BaseError extends Exception {
    private final String message;

    protected BaseError(final String message) {
        super(message);
        this.message = message;
    }
}

