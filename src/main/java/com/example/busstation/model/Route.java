package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Route {

    private String id;
    private BusStation origin;
    private BusStation destination;
    private float distance;
    private List<BusTrip> trips = new ArrayList<>();

    public Route() {
    }

    public Route(String id, BusStation origin, BusStation destination, float distance, List<BusTrip> trips) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.origin = Objects.requireNonNull(origin, "origin is required");
        this.destination = Objects.requireNonNull(destination, "destination is required");
        this.distance = distance;
        if (trips != null) {
            this.trips = new ArrayList<>(trips);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id is required");
    }

    public BusStation getOrigin() {
        return origin;
    }

    public void setOrigin(BusStation origin) {
        this.origin = Objects.requireNonNull(origin, "origin is required");
    }

    public BusStation getDestination() {
        return destination;
    }

    public void setDestination(BusStation destination) {
        this.destination = Objects.requireNonNull(destination, "destination is required");
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        if (distance < 0) {
            throw new IllegalArgumentException("distance cannot be negative");
        }
        this.distance = distance;
    }

    public List<BusTrip> getTrips() {
        return Collections.unmodifiableList(trips);
    }

    public void setTrips(List<BusTrip> trips) {
        this.trips = (trips != null) ? new ArrayList<>(trips) : new ArrayList<>();
    }

    public void addTrip(BusTrip trip) {
        Objects.requireNonNull(trip, "trip is required");
        trips.add(trip);
    }

    public boolean removeTrip(BusTrip trip) {
        return trips.remove(trip);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id='" + id + '\'' +
                ", origin=" + origin +
                ", destination=" + destination +
                ", distance=" + distance +
                ", trips=" + trips +
                '}';
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

