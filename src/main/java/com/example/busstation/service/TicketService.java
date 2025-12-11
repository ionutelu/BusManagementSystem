package com.example.busstation.service;

import com.example.busstation.exception.DuplicateRouteException;
import com.example.busstation.exception.DuplicateSeatException;
import com.example.busstation.model.Route;
import com.example.busstation.model.Ticket;
import com.example.busstation.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepo;

    @Autowired
    public TicketService(TicketRepository ticketRepo) {
        this.ticketRepo = ticketRepo;
    }

    public Ticket save(Ticket ticket){
        try {
            return ticketRepo.save(ticket);
        }catch (DataIntegrityViolationException e){
            throw new DuplicateSeatException("Seat already exists!");
        }

    }

    public List<Ticket> findAll(){
        return ticketRepo.findAll();
    }

    public Ticket findById(long id){
        return ticketRepo.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found: " + id));
    }

    public void deleteById(long id){
        ticketRepo.deleteById(id);
    }

}
