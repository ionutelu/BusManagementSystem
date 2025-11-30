package com.example.busstation.model;

import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "tickets")
public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bus_trip_id", nullable = false)
    private BusTrip busTrip;

    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    private Passenger passenger;

    private String seatNumber;

    private double price;

    public Ticket() {
    }

    public Ticket(BusTrip busTrip, Passenger passenger, String seatNumber, double price) {

        this.busTrip = busTrip;
        this.passenger = passenger;
        this.seatNumber = seatNumber;
        this.price = price;
    }

    public Long getId() {
        return id;
    }
//    @Override
//    public void setId(String id) {
//        this.id = id;
//    }

    public BusTrip getBusTrip() {
        return busTrip;
    }

    public void setTripId(BusTrip busTrip) {
        this.busTrip = busTrip;
    }

    public Passenger getPassenger() {
        return passenger;
    }

    public void setPassengerId(Passenger passenger) {
        this.passenger = passenger;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id='" + id + '\'' +
                ", tripId='" + busTrip.getId() + '\'' +
                ", passengerId='" + passenger.getId() + '\'' +
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

