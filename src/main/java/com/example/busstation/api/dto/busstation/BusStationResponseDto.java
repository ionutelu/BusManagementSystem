package com.example.busstation.api.dto.busstation;

public class BusStationResponseDto {

    private Long id;
    private String name;
    private String city;
    private Boolean isDamaged;

    public BusStationResponseDto() {}

    public BusStationResponseDto(Long id, String name, String city, Boolean isDamaged) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.isDamaged = isDamaged;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public Boolean getIsDamaged() { return isDamaged; }
    public void setIsDamaged(Boolean isDamaged) { this.isDamaged = isDamaged; }
}

