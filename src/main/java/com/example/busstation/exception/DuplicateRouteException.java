package com.example.busstation.exception;

public class DuplicateRouteException extends RuntimeException {
    public DuplicateRouteException(String message) {
        super(message);
    }
}
