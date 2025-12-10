package com.example.busstation.controller;

import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Passenger;
import com.example.busstation.model.Ticket;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.PassengerService;
import com.example.busstation.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final PassengerService passengerService;
    private final BusTripService busTripService;

    public TicketController(TicketService ticketService, PassengerService passengerService, BusTripService busTripService){
        this.ticketService = ticketService;
        this.passengerService = passengerService;
        this.busTripService = busTripService;
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
    public String create(@RequestParam Long busTripId, @RequestParam Long passengerId, @ModelAttribute Ticket ticket){

        BusTrip busTrip = busTripService.findById(busTripId);
        Passenger passenger = passengerService.findById(passengerId);

        ticket.setBusTrip(busTrip);
        ticket.setPassenger(passenger);

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
    public String update(@RequestParam Long busTripId, @RequestParam Long passengerId, @PathVariable Long id, @ModelAttribute Ticket ticket) {

        BusTrip busTrip = busTripService.findById(busTripId);
        Passenger passenger = passengerService.findById(passengerId);

        ticket.setBusTrip(busTrip);
        ticket.setPassenger(passenger);

        Ticket existing = ticketService.findById(id);

        existing.setBusTrip(ticket.getBusTrip());
        existing.setPassenger(ticket.getPassenger());
        existing.setPrice(ticket.getPrice());
        existing.setSeatNumber(ticket.getSeatNumber());

        ticketService.save(existing);

        return "redirect:/tickets";
    }
}
