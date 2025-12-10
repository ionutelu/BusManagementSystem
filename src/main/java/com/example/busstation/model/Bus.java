package com.example.busstation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.Objects;
@Entity
@Table(name = "buses")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Bus{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "vin", nullable = false, unique = true)
    private String vin;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;
    private int capacity;
    private BusStatus status;

    public Bus() {
        this.status = BusStatus.DOWN;
    }
    public Bus(String registrationNumber, int capacity, String vin) {
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = BusStatus.DOWN;
        this.vin = vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }
    public String getVin() {
        return vin;
    }

    public Long getId() { return id; }
    void setId(Long id){ this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @JsonProperty("statusEnum")
    public BusStatus getStatus() { return status; }
    @JsonProperty("statusDescription")
    public String getStatusDescription() { return status.getDescription(); }
    @JsonProperty("statusEnum")
    public void setStatus(BusStatus status) { this.status = status; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bus)) return false;
        Bus bus = (Bus) o;
        return Objects.equals(id, bus.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Bus{" +
                "id='" + id + '\'' +
                ", registrationNumber='" + registrationNumber + '\'' +
                ", capacity=" + capacity +
                ", status=" + status +
                '}';
    }
}
