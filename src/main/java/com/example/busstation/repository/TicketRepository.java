package com.example.busstation.repository;
import com.example.busstation.model.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public class TicketRepository extends InMemoryRepository<String, Ticket>{

}
