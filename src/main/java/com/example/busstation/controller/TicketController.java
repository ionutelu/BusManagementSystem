package com.example.busstation.controller;

import com.example.busstation.model.Ticket;
import com.example.busstation.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService){
        this.ticketService = ticketService;
    }

    @GetMapping
    public String listTickets(Model model){
        model.addAttribute("tickets", ticketService.findAll());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String showForm(Ticket ticket){
        return "ticket/form";
    }

    @PostMapping
    public String addTicket(Ticket ticket){
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String deleteTicket(@PathVariable String id) {
        ticketService.deleteById(id);
        return "redirect:/tickets";
    }
}
