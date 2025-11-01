package com.example.busstation.model;

import java.util.Objects;

public class Ticket implements Identifiable<String>{

    private String id;
    private String tripId;
    private String passengerId;
    private String seatNumber;
    private double price;

    public Ticket() {
    }

    public Ticket(String id, String tripId, String passengerId, String seatNumber, double price) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.tripId = Objects.requireNonNull(tripId, "tripId is required");
        this.passengerId = Objects.requireNonNull(passengerId, "passengerId is required");
        this.seatNumber = Objects.requireNonNull(seatNumber, "seatNumber is required");
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.price = price;
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id is required");
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = Objects.requireNonNull(tripId, "tripId is required");
    }

    public String getPassengerId() {
        return passengerId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = Objects.requireNonNull(passengerId, "passengerId is required");
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = Objects.requireNonNull(seatNumber, "seatNumber is required");
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        if (price < 0) {
            throw new IllegalArgumentException("price cannot be negative");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", tripId='" + tripId + '\'' +
                ", passengerId='" + passengerId + '\'' +
                ", seatNumber='" + seatNumber + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ticket)) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

