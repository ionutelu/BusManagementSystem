package com.example.busstation.api.dto.bus;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BusRequestDto {

    @NotBlank(message = "VIN is required")
    private String vin;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @Min(value = 20, message = "Capacity must be at least 20")
    @Max(value = 80, message = "Capacity must be at most 80")
    private int capacity;

    @NotNull(message = "Status is required")
    private String status;

    public String getVin() { return vin; }
    public void setVin(String vin) { this.vin = vin; }

    public String getRegistrationNumber() { return registrationNumber; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }

    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

