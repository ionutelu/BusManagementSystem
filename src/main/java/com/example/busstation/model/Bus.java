package com.example.busstation.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Bus implements Identifiable<String> {

    private String id;
    private String vin;
    private String registrationNumber;
    private int capacity;
    private BusStatus status;

    public Bus() {
        this.status = BusStatus.DOWN;
    }
    public Bus(String id, String registrationNumber, int capacity, String vin) {
        this.id = id;
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
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    @JsonProperty("statusEnum")
    public BusStatus getStatusEnum() { return status; }
    @JsonProperty("statusDescription")
    public String getStatusDescription() { return status.getDescription(); }
    @JsonProperty("statusEnum")
    public void setStatusEnum(BusStatus status) { this.status = status; }

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
