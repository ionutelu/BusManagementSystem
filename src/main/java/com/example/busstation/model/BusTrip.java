package com.example.busstation.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class BusTrip implements Identifiable<String>{

    private String id;
    private String routeId;
    private String busId;
    private LocalDateTime startTime;
    private List<Ticket> tickets = new ArrayList<>();
    private List<DutyAssignment> assignments = new ArrayList<>();
    private BusTripStatus status = BusTripStatus.PLANNED;

    public BusTrip() {}

    public BusTrip(String id, String routeId, String busId, LocalDateTime startTime,
                   List<Ticket> tickets, List<DutyAssignment> assignments, BusTripStatus status) {
        this.id = id;
        this.routeId = routeId;
        this.busId = busId;
        // this.startTime = Objects.requireNonNull(startTime, "startTime is required");
        this.startTime = startTime;
        this.tickets = tickets;
        this.assignments = assignments;
        this.status = status;
    }
    @Override
    public String getId() { return id; }
    @Override
    public void setId(String id) { this.id = id; }

    public String getRouteId() { return routeId; }
    public void setRouteId(String routeId) { this.routeId = routeId; }

    public String getBusId() { return busId; }
    public void setBusId(String busId) { this.busId = busId; }

    public LocalDateTime getStartTime() { return startTime; }
    public void setStartTime(LocalDateTime startTime) { this.startTime = startTime; }

    public List<Ticket> getTickets() { return Collections.unmodifiableList(tickets); }
    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<DutyAssignment> getAssignments() { return Collections.unmodifiableList(assignments); }
    public void setAssignments(List<DutyAssignment> assignments) {
        this.assignments = assignments;
    }

    public BusTripStatus getStatus() { return status; }
    public void setStatus(BusTripStatus status) {
        this.status = status;
    }


    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void addAssignment(DutyAssignment assignment) {
        assignments.add(assignment);
    }

    @Override
    public String toString() {
        return "BusTrip{" +
                "id='" + id + '\'' +
                ", routeId='" + routeId + '\'' +
                ", busId='" + busId + '\'' +
                ", startTime=" + startTime +
                ", tickets=" + tickets +
                ", assignments=" + assignments +
                ", status=" + status +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BusTrip)) return false;
        BusTrip busTrip = (BusTrip) o;
        return Objects.equals(id, busTrip.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
