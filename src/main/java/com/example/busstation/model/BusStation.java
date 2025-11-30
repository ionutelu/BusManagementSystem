package com.example.busstation.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "bus_stations")
public class BusStation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private Boolean isDamaged;

    //private List<BusTrip> trips = new ArrayList<>();

    public BusStation() {
    }

    public BusStation(String name, String city, List<BusTrip> trips, Boolean isDamaged) {

        this.name = name;
        this.city = city;
        this.isDamaged = isDamaged;
        //this.trips = trips;
    }


    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    public Long getId() { return id; }

//    @Override
//    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

//    public List<BusTrip> getTrips() { return Collections.unmodifiableList(trips); }
//
//    public void setTrips(List<BusTrip> trips) {
//        if (trips == null) {
//            this.trips = new ArrayList<>();
//        } else {
//            this.trips = new ArrayList<>(trips);
//        }
//    }
//
//    public void addTrip(BusTrip trip) {
//        this.trips.add(trip);
//    }
//
//    public boolean removeTrip(BusTrip trip) {
//        return this.trips.remove(trip);
//    }

//    @Override
//    public String toString() {
//        return "BusStation{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", city='" + city + '\'' +
//                ", trips=" + trips +
//                '}';
//    }

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