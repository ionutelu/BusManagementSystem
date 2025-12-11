package com.example.busstation.exception;

public class BusStatusInvalid extends RuntimeException {
    public BusStatusInvalid(String message) {
        super(message);
    }
}
