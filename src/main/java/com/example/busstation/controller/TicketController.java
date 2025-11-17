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
    public String index(Model model){
        model.addAttribute("tickets", ticketService.findAll());
        return "ticket/index";
    }

    @GetMapping("/new")
    public String form(Ticket ticket){
        return "ticket/form";
    }

    @PostMapping
    public String create(Ticket ticket){
        ticketService.save(ticket);
        return "redirect:/tickets";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        ticketService.deleteById(id);
        return "redirect:/tickets";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("ticket", ticketService.findById(id));
        return "ticket/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Ticket ticket) {
        ticketService.update(ticket, id);
        return "redirect:/tickets";
    }
}
