package com.example.busstation.exception;

public class DuplicateSeatException extends RuntimeException {
    public DuplicateSeatException(String message) {
        super(message);
    }
}
