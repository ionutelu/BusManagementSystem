package com.example.busstation.api.dto.passenger;

public class PassengerResponseDto {

    private Long id;
    private String name;
    private String currency;

    public PassengerResponseDto() {}

    public PassengerResponseDto(Long id, String name, String currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
}

