package com.example.busstation.model;

public enum DriverRole {
    PRIMARY_DRIVER("Primary driver"),
    RESERVE_DRIVER("Reserve driver");

    private final String description;

    DriverRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
