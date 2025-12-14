package com.example.busstation.exception;

public class InvalidBusStatusException extends RuntimeException {
    public InvalidBusStatusException(String value) {
        super("Invalid BusStatus: '" + value + "'. Allowed values: DOWN, ACTIVE");
    }
}