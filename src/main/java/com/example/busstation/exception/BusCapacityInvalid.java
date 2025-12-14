package com.example.busstation.exception;

public class BusCapacityInvalid extends RuntimeException {
    public BusCapacityInvalid(String message) {
        super(message);
    }
}
