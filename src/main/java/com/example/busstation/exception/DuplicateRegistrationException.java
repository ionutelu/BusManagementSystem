package com.example.busstation.exception;

public class DuplicateRegistrationException extends RuntimeException {
    public DuplicateRegistrationException(String message) {
        super(message);
    }
}
