package com.example.busstation.api.dto.busstation;

import jakarta.validation.constraints.NotBlank;

public class BusStationRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "City is required")
    private String city;

    private Boolean isDamaged;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Boolean getIsDamaged() { return isDamaged; }
    public void setIsDamaged(Boolean isDamaged) { this.isDamaged = isDamaged; }
}

