package com.example.busstation.api.dto.bustrip;

import com.example.busstation.api.dto.busstation.BusStationResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class BusTripResponseDto {

    private Long id;
    private Long routeId;
    private String routeSummary;
    private Long busId;
    private String busRegistration;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    private String status;
    private int ticketCount;
    private int assignmentCount;
    private List<BusStationResponseDto> stops;

    public BusTripResponseDto() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRouteId() { return routeId; }
    public void setRouteId(Long routeId) { this.routeId = routeId; }

    public String getRouteSummary() { return routeSummary; }
    public void setRouteSummary(String routeSummary) { this.routeSummary = routeSummary; }

    public Long getBusId() { return busId; }
    public void setBusId(Long busId) { this.busId = busId; }

    public String getBusRegistration() { return busRegistration; }
    public void setBusRegistration(String busRegistration) { this.busRegistration = busRegistration; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public int getTicketCount() { return ticketCount; }
    public void setTicketCount(int ticketCount) { this.ticketCount = ticketCount; }

    public int getAssignmentCount() { return assignmentCount; }
    public void setAssignmentCount(int assignmentCount) { this.assignmentCount = assignmentCount; }

    public List<BusStationResponseDto> getStops() { return stops; }
    public void setStops(List<BusStationResponseDto> stops) { this.stops = stops; }
}

