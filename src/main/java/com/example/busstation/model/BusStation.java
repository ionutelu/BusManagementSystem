package com.example.busstation.model;

import jakarta.persistence.*;

import java.util.*;

@Entity
@Table(name = "bus_stations", uniqueConstraints = @UniqueConstraint(columnNames = {"name", "city"}))
public class BusStation{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String city;
    private Boolean isDamaged;

    @ManyToMany(mappedBy = "busStations")
    private Set<BusTrip> trips = new HashSet<>();


    public BusStation() {
    }

    public BusStation(String name, String city, List<BusTrip> trips, Boolean isDamaged) {

        this.name = name;
        this.city = city;
        this.isDamaged = isDamaged;

    }


    public Boolean getDamaged() {
        return isDamaged;
    }

    public void setDamaged(Boolean damaged) {
        isDamaged = damaged;
    }

    public Long getId() { return id; }

    void setId(Long id){ this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Set<BusTrip> getTrips() { return trips; }
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