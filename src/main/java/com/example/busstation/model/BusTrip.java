package com.example.busstation.model;

import java.time.LocalDateTime;
import java.util.List;

public class BusTrip {

    public enum BusTripStatus{
        PLANNED,
        ACTIVE,
        COMPLETED
    }
    String id;
    String routeId;
    String busId;
    LocalDateTime startTime;
    List<Ticket> tickets;
    List<DutyAssignment> assignments;
    BusTripStatus status;

    public BusTrip(String id, String routeId, String busId, LocalDateTime startTime,
                   List<Ticket> tickets, List<DutyAssignment> assignments, BusTripStatus status) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        this.startTime = startTime;
        this.tickets = tickets;
        this.assignments = assignments;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public String getRouteId() {
        return routeId;
    }

    public String getBusId() {
        return busId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public List<DutyAssignment> getAssignments() {
        return assignments;
    }

    public BusTripStatus getStatus() {
        return status;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
    }

    public void setStatus(BusTripStatus status) {
        this.status = status;
    }

}
