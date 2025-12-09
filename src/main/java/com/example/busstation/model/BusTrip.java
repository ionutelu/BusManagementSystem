package com.example.busstation.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Entity
@Table (name = "bus_trips")
public class BusTrip{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "bus_id", nullable = false)
    private Bus bus;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;

    @OneToMany(mappedBy = "busTrip", cascade = CascadeType.ALL)
    private List<Ticket> tickets = new ArrayList<>();

    @OneToMany(mappedBy = "busTrip", cascade = CascadeType.ALL)
    private List<DutyAssignment> assignments = new ArrayList<>();

    private BusTripStatus status = BusTripStatus.PLANNED;

    public BusTrip() {}

    public BusTrip(Route route, Bus bus, LocalDateTime startTime,
                   List<Ticket> tickets, List<DutyAssignment> assignments, BusTripStatus status) {

        this.route = route;
        this.bus = bus;
        // this.startTime = Objects.requireNonNull(startTime, "startTime is required");
        this.startTime = startTime;
        this.tickets = tickets;
        this.assignments = assignments;
        this.status = status;
    }

    public Long getId() { return id; }

//    @Override
//    public void setId(String id) { this.id = id; }


    public Route getRoute() { return route; }
    public void setRoute(Route route) { this.route = route; }

    public Bus getBus() { return bus; }
    public void setBus(Bus bus) { this.bus = bus; }


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
                ", routeId='" + route.getId() + '\'' +
                ", busId='" + bus.getId() + '\'' +
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
