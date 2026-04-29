package com.example.busstation.api.dto.bus;

public class BusResponseDto {

    private Long id;
    private String vin;
    private String registrationNumber;
    private int capacity;
    private String status;

    public BusResponseDto() {}

    public BusResponseDto(Long id, String vin, String registrationNumber, int capacity, String status) {
        this.id = id;
        this.vin = vin;
        this.registrationNumber = registrationNumber;
        this.capacity = capacity;
        this.status = status;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

