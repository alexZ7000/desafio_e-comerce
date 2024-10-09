package com.example.comerce.shared.helpers.errors;

public class EntityErrors extends BaseError {

    private EntityErrors(final String message) {
        super("Field " + message + " is not valid");
    }

    public static class EntityError extends EntityErrors {
        public EntityError(final String message){
            super(message);
        }
    }

    public static class EntityParameterTypeError extends EntityError {
        public EntityParameterTypeError(final String message) {
            super(message);
        }
    }

    public static class EntityParameterError extends EntityError {
        public EntityParameterError(final String message) {
            super(message);
        }
    }
}
