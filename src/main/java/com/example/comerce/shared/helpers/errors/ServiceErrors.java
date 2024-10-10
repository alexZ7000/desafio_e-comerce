package com.example.comerce.shared.helpers.errors;

public final class ServiceErrors {
    public final static class NoItemsFound extends BaseError {
        public NoItemsFound(final String message) {
            super("No items found for " + message);
        }
    }

    public final static class DuplicatedItem extends BaseError {
        public DuplicatedItem(final String message) {
            super("The item already exists for this " + message);
        }
    }

    public final static class ForbiddenAction extends BaseError {
        public ForbiddenAction(final String message) {
            super("The action is forbidden for this " + message);
        }
    }
}
