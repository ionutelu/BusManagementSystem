package com.example.busstation.repository;
import com.example.busstation.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InFileRepository<String, Ticket> {
    TicketRepository(){
        super("src/main/resources/data/Ticket.json", Ticket[].class);
    }
}
