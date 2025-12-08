package com.example.busstation.exception;

public class InvalidStationException extends RuntimeException {
    public InvalidStationException(String message) {
        super(message);
    }
}
