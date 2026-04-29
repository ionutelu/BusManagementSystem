package com.example.busstation.api.dto.route;

public class RouteResponseDto {

    private Long id;
    private Long originStationId;
    private String originName;
    private String originCity;
    private Long destinationStationId;
    private String destinationName;
    private String destinationCity;
    private float distance;

    public RouteResponseDto() {}

    public RouteResponseDto(Long id, Long originStationId, String originName, String originCity,
                             Long destinationStationId, String destinationName, String destinationCity,
                             float distance) {
        this.id = id;
        this.originStationId = originStationId;
        this.originName = originName;
        this.originCity = originCity;
        this.destinationStationId = destinationStationId;
        this.destinationName = destinationName;
        this.destinationCity = destinationCity;
        this.distance = distance;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getOriginStationId() { return originStationId; }
    public void setOriginStationId(Long originStationId) { this.originStationId = originStationId; }

    public String getOriginName() { return originName; }
    public void setOriginName(String originName) { this.originName = originName; }

    public String getOriginCity() { return originCity; }
    public void setOriginCity(String originCity) { this.originCity = originCity; }

    public Long getDestinationStationId() { return destinationStationId; }
    public void setDestinationStationId(Long destinationStationId) { this.destinationStationId = destinationStationId; }

    public String getDestinationName() { return destinationName; }
    public void setDestinationName(String destinationName) { this.destinationName = destinationName; }

    public String getDestinationCity() { return destinationCity; }
    public void setDestinationCity(String destinationCity) { this.destinationCity = destinationCity; }

    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }
}

