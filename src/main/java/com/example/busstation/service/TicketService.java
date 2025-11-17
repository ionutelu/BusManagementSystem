package com.example.busstation.service;

import com.example.busstation.model.Route;
import com.example.busstation.model.Ticket;
import com.example.busstation.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;

    @Autowired
    public TicketService(TicketRepository ticketRepo) {
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

    public Ticket update(Ticket updatedEntity, String id) { return ticketRepo.update(updatedEntity, id);}
}
