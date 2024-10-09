package com.example.comerce.shared.helpers.errors;

public class ServiceErrors {
    public static class NoItemsFound extends BaseError {
        public NoItemsFound(final String message) {
            super("No items found for " + message);
        }
    }

    public static class DuplicatedItem extends BaseError {
        public DuplicatedItem(final String message) {
            super("The item already exists for this " + message);
        }
    }

    public static class ForbiddenAction extends BaseError {
        public ForbiddenAction(final String message) {
            super("The action is forbidden for this " + message);
        }
    }
}
