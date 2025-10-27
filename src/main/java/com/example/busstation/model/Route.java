package com.example.busstation.model;

import java.util.List;

public class Route {
    String id;
    BusStation origin;
    BusStation destination;
    float distance;
    List<BusTrip> trips;

    public Route(String id, BusStation origin, BusStation destination, float distance, List<BusTrip> trips) {
        this.id = id;
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
        this.trips = trips;
    }

    public String getId() {
        return id;
    }

    public BusStation getOrigin() {
        return origin;
    }

    public BusStation getDestination() {
        return destination;
    }

    public float getDistance() {
        return distance;
    }

    public List<BusTrip> getTrips() {
        return trips;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrigin(BusStation origin) {
        this.origin = origin;
    }

    public void setDestination(BusStation destination) {
        this.destination = destination;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setTrips(List<BusTrip> trips) {
        this.trips = trips;
    }
}
