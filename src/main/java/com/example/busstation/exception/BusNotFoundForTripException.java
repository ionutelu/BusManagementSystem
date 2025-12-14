package com.example.busstation.exception;

public class BusNotFoundForTripException extends RuntimeException {
    public BusNotFoundForTripException(String message) {
        super(message);
    }
}
