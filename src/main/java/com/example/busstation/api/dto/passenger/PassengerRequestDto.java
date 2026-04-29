package com.example.busstation.api.dto.passenger;

import jakarta.validation.constraints.NotBlank;

public class PassengerRequestDto {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Currency is required")
    private String currency;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}

