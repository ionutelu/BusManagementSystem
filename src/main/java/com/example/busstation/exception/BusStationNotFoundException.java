package com.example.busstation.exception;

public class BusStationNotFoundException extends RuntimeException {
    public BusStationNotFoundException(String message) {
        super(message);
    }
}
