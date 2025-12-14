package com.example.busstation.model;

import com.example.busstation.exception.InvalidBusStatusException;

public enum BusStatus {
    DOWN("Down"),
    ACTIVE("Active");

    private final String description;

    BusStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    // VALIDARE + CONVERSIE
    public static BusStatus fromString(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidBusStatusException(value);
        }

        for (BusStatus status : BusStatus.values()) {
            // acceptă: DOWN / down / Down / Active etc.
            if (status.name().equalsIgnoreCase(value)
                    || status.description.equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new InvalidBusStatusException(value);
    }
}
