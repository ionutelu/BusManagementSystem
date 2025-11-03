package com.example.busstation.service;

import com.example.busstation.model.Ticket;
import com.example.busstation.repository.TicketRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepo ticketRepo;

    @Autowired
    public TicketService(TicketRepo ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public void save(Ticket ticket){
        ticketRepo.save(ticket);
    }

    public List<Ticket> findAll(){
        return ticketRepo.findAll();
    }

    public Ticket findById(String id){
        return ticketRepo.findById(id);
    }

    public boolean deleteById(String id){
        return ticketRepo.deleteById(id);
    }
}
