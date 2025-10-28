package com.example.busstation.model;

public enum BusTripStatus {
    PLANNED("Planned"),
    ACTIVE("Active"),
    COMPLETED("Completed");

    private final String status;

    BusTripStatus(String status) {
        this.status = status;
    }

    public String getstatus() {
        return status;
    }
}
