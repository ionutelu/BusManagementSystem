package com.example.busstation.repository;

import com.example.busstation.model.Staff;
import com.example.busstation.model.Ticket;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class TicketRepo implements AbstractRepository<Ticket> {

    private final List<Ticket> ticketRepo = new ArrayList<>();

    public TicketRepo() {
        ticketRepo.add(new Ticket("1", "1", "1", "A1", 50.0));
        ticketRepo.add(new Ticket("2", "2", "2", "B2", 75.0));
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepo;
    }

    @Override
    public Ticket findById(String id) {
        String strId = String.valueOf(id);
        for (Ticket ticket : ticketRepo) {
            if (ticket.getId().equals(strId)) {
                return ticket;
            }
        }
        return null;
    }

    @Override
    public Ticket save(Ticket ticket) {
        Objects.requireNonNull(ticket, "ticket is required");
        Ticket existing = findById(ticket.getId());
        if (existing != null) {
            ticketRepo.remove(existing);
        }
        ticketRepo.add(ticket);
        return ticket;
    }
    @Override
    public boolean deleteById(String id) {
        for (Ticket ticket : ticketRepo) {
            if (ticket.getId().equals(id)) {
                ticketRepo.remove(ticket);
                return true;
            }
        }
        return false;
    }

}
