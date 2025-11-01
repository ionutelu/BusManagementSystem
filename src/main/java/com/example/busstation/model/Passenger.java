package com.example.busstation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Passenger implements Identifiable<String>{

    private String id;
    private String name;
    private String currency;
    private List<Ticket> tickets = new ArrayList<>();

    public Passenger() {
    }

    public Passenger(String id, String name, String currency, List<Ticket> tickets) {
        this.id = Objects.requireNonNull(id, "id is required");
        this.name = Objects.requireNonNull(name, "name is required");
        this.currency = Objects.requireNonNull(currency, "currency is required");
        if (tickets != null) {
            this.tickets = new ArrayList<>(tickets);
        }
    }
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(String id) {
        this.id = Objects.requireNonNull(id, "id is required");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = Objects.requireNonNull(name, "name is required");
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = Objects.requireNonNull(currency, "currency is required");
    }

    public List<Ticket> getTickets() {
        return Collections.unmodifiableList(tickets);
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = (tickets != null) ? new ArrayList<>(tickets) : new ArrayList<>();
    }

    public void addTicket(Ticket ticket) {
        Objects.requireNonNull(ticket, "ticket is required");
        this.tickets.add(ticket);
    }

    public boolean removeTicket(Ticket ticket) {
        return this.tickets.remove(ticket);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", currency='" + currency + '\'' +
                ", tickets=" + tickets +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Passenger)) return false;
        Passenger that = (Passenger) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
