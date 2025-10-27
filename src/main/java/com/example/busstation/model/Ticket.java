package com.example.busstation.model;

public class Ticket {
    String id;
    String tripId;
    String passengerId;
    String seatNumber;
    double price;

    public Ticket(String id, String tripId, String passengerId, String seatNumber, double price) {
        this.id = id;
        this.tripId = tripId;
        this.passengerId = passengerId;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    // Getters
    public String getId() {
        return id;
    }

    public String getTripId() {
        return tripId;
    }

    public String getPassengerId() {
        return passengerId;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }

    public void setPassengerId(String passengerId) {
        this.passengerId = passengerId;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
