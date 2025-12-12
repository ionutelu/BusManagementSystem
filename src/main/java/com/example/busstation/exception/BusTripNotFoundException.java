package com.example.busstation.exception;

public class BusTripNotFoundException extends RuntimeException {
    public BusTripNotFoundException(String message) {
        super(message);
    }
}
