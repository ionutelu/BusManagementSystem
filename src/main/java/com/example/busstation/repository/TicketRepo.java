package com.example.busstation.repository;

import com.example.busstation.model.Ticket;
import com.example.busstation.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
    public Ticket findById(int id) {
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
        ticketRepo.add(ticket);
        return ticket;
    }
}
