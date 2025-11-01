package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BusStation implements Identifiable<String>{

    private String id;
    private String name;
    private String city;
    private Boolean isDamaged;
    private List<BusTrip> trips = new ArrayList<>();

    public BusStation() {
    }

    public BusStation(String id, String name, String city, List<BusTrip> trips, Boolean isDamaged) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.name = Objects.requireNonNull(name, "name is required");
        this.city = Objects.requireNonNull(city, "city is required");
        this.isDamaged = isDamaged;
        if (trips != null) {
            this.trips = new ArrayList<>(trips);
        }
    }


    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = Objects.requireNonNull(id, "id is required"); }

    public String getName() { return name; }
    public void setName(String name) { this.name = Objects.requireNonNull(name, "name is required"); }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = Objects.requireNonNull(city, "city is required"); }

    public List<BusTrip> getTrips() { return Collections.unmodifiableList(trips); }

    public void setTrips(List<BusTrip> trips) {
        if (trips == null) {
            this.trips = new ArrayList<>();
        } else {
            this.trips = new ArrayList<>(trips);
        }
    }

    public void addTrip(BusTrip trip) {
        Objects.requireNonNull(trip, "trip is required");
        this.trips.add(trip);
    }

    public boolean removeTrip(BusTrip trip) {
        return this.trips.remove(trip);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusStation)) return false;
        BusStation that = (BusStation) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}