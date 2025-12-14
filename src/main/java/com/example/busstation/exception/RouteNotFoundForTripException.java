package com.example.busstation.exception;

public class RouteNotFoundForTripException extends RuntimeException {
    public RouteNotFoundForTripException(String message) {
        super(message);
    }
}
