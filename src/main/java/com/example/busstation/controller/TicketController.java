package com.example.busstation.controller;

import com.example.busstation.model.Passenger;
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
    public String index(Model model){
        model.addAttribute("tickets", ticketService.findAll());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("ticket", new Ticket());
        return "ticket/form";
    }

    @PostMapping
    public String create(Ticket ticket){
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        ticketService.deleteById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "ticket/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Ticket ticket) {

        Ticket existing = ticketService.findById(id);

        existing.setBusTrip(ticket.getBusTrip());
        existing.setPassenger(ticket.getPassenger());
        existing.setPrice(ticket.getPrice());
        existing.setSeatNumber(ticket.getSeatNumber());

        ticketService.save(existing);
        return "redirect:/tickets";
    }
}
