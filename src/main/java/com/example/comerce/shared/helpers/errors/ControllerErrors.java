package com.example.comerce.shared.helpers.errors;


public final class ControllerErrors {
    public final static class MissingParameters extends BaseError {
        public MissingParameters(final String fieldName) {
            super("Field " + fieldName + " is missing");
        }
    }

    public final static class WrongTypeParameters extends BaseError {
        public WrongTypeParameters(final String fieldName, final String fieldTypeExpected, final String fieldTypeReceived) {
            super("Field " + fieldName + " isn't in the right type.\n Received: " + fieldTypeReceived + ".\n Expected: " + fieldTypeExpected + ".");
        }
    }
}


