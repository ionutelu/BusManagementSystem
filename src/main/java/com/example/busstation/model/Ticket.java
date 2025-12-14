package com.example.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;

import java.util.Objects;
@Entity
@Table(name = "tickets", uniqueConstraints = @UniqueConstraint(columnNames = {"seat_number", "bus_trip_id"}))
public class Ticket{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "bus_trip_id", nullable = false)
    //@NotNull(message = "Trip is required.")
    private BusTrip busTrip;


    @ManyToOne
    @JoinColumn(name = "passenger_id", nullable = false)
    //@NotNull(message = "Passenger is required")
    private Passenger passenger;


    @NotBlank(message = "Seat is required")
    private String seatNumber;

    @NotNull(message = "Price required")
    @Positive(message = "Price must be positive")
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

    void setId(Long id){ this.id = id; }

    public BusTrip getBusTrip() {
        return busTrip;
    }

    public void setBusTrip(BusTrip busTrip) {
        this.busTrip = busTrip;
    }

    public void setPassenger(Passenger passenger) {
        this.passenger = passenger;
    }

    public Passenger getPassenger() {
        return passenger;
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

