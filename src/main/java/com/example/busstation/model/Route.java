package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Route implements Identifiable<String>{

    private String id;
    private BusStation origin;
    private BusStation destination;
    private float distance;
    private List<BusTrip> trips = new ArrayList<>();

    public Route() {
    }

    public Route(String id, BusStation origin, BusStation destination, float distance, List<BusTrip> trips) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.trips = new ArrayList<>(trips);
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = id;
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

