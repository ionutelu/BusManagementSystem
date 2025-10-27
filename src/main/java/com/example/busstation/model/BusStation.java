package com.example.busstation.model;

import java.util.List;

public class BusStation {
    public String id;
    public String name;
    public String city;
    public List<BusTrip> trips;

    public BusStation() {
    }

    public BusStation(String id, String name, String city, List<BusTrip> trips) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.trips = trips;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<BusTrip> getTrips() {
        return trips;
    }

    public void setTrips(List<BusTrip> trips) {
        this.trips = trips;
    }

    @Override
    public String toString() {
        return "BusStation{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", trips=" + trips +
                '}';
    }
}
