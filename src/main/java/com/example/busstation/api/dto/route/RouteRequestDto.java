package com.example.busstation.api.dto.route;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RouteRequestDto {

    @NotNull(message = "Origin station ID is required")
    private Long originStationId;

    @NotNull(message = "Destination station ID is required")
    private Long destinationStationId;

    @Positive(message = "Distance must be greater than 0")
    private float distance;

    public Long getOriginStationId() { return originStationId; }
    public void setOriginStationId(Long originStationId) { this.originStationId = originStationId; }

    public Long getDestinationStationId() { return destinationStationId; }
    public void setDestinationStationId(Long destinationStationId) { this.destinationStationId = destinationStationId; }

    public float getDistance() { return distance; }
    public void setDistance(float distance) { this.distance = distance; }
}

