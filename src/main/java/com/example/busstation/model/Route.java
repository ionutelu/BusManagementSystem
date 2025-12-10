package com.example.busstation.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.*;

@Entity
@Table(name = "routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Origin station is required.")
    @ManyToOne
    @JoinColumn(name = "origin_station_id", nullable = false)
    private BusStation origin;

    @NotNull(message = "Destination station is required.")
    @ManyToOne
    @JoinColumn(name = "destination_station_id", nullable = false)
    private BusStation destination;

    @Positive(message = "Distance must be greater than 0.")
    private float distance;

    @OneToMany(mappedBy = "route")
    private List<BusTrip> trips = new ArrayList<>();

    public Route() {}

    void setId(Long id){
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public BusStation getOrigin() {
        return origin;
    }

    public void setOrigin(BusStation origin) {
        this.origin = origin;
    }

    public BusStation getDestination() {
        return destination;
    }

    public void setDestination(BusStation destination) {
        this.destination = destination;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public List<BusTrip> getTrips() {
        return Collections.unmodifiableList(trips);
    }

    public void setTrips(List<BusTrip> trips) {
        this.trips = trips;
    }

    public void addTrip(BusTrip trip) {
        trips.add(trip);
    }

    public boolean removeTrip(BusTrip trip) {
        return trips.remove(trip);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Route)) return false;
        Route route = (Route) o;
        return Objects.equals(id, route.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
