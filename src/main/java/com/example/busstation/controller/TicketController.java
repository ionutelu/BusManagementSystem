package com.example.busstation.controller;

import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Passenger;
import com.example.busstation.model.Ticket;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.PassengerService;
import com.example.busstation.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("tickets", ticketService.findAll());
//        return "ticket/index";
//    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            Model model
    ) {
        model.addAttribute(
                "tickets",
                ticketService.findAllSorted(sortField, sortDirection)
        );
        return "ticket/index";
    }


    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("ticket", new Ticket());
        return "ticket/form";
    }

//    @PostMapping
//    public String create(@RequestParam Long busTripId,
//                         @RequestParam Long passengerId,
//                         @Valid @ModelAttribute Ticket ticket,
//                         BindingResult result,
//                         Model model){
//
//        if (result.hasErrors()) {
//            return "busStation/form";
//        }
//
//        BusTrip busTrip = busTripService.findById(busTripId);
//        Passenger passenger = passengerService.findById(passengerId);
//
//
//
//
//        ticket.setBusTrip(busTrip);
//
//        ticket.setPassenger(passenger);
//
//        ticketService.save(ticket);
//
//        return "redirect:/tickets";
//    }

    @PostMapping
    public String create(@RequestParam(required = false) Long busTripId,
                         @RequestParam(required = false) Long passengerId,
                         @Valid @ModelAttribute("ticket") Ticket ticket,
                         BindingResult bindingResult,
                         Model model) {




//        if(bindingResult.hasErrors()) {
//            bindingResult.getAllErrors().forEach(e->model.addAttribute("errorMessage", e.getDefaultMessage()));
//            model.addAttribute("busTripId", busTripId);
//            model.addAttribute("passengerId", passengerId);
//            return "ticket/form";
//        }

        if(busTripId == null){
            model.addAttribute("errorMessage", "Bus trip required");
            return "ticket/form";
        }

        if(passengerId == null){
            model.addAttribute("errorMessage", "Passenger required");
            return "ticket/form";
        }

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e->model.addAttribute("errorMessage", e.getDefaultMessage()));
            return "ticket/form";
        }

        try{
            BusTrip busTrip = busTripService.findById(busTripId);
            Passenger passenger = passengerService.findById(passengerId);
            ticket.setBusTrip(busTrip);
            ticket.setPassenger(passenger);
        }catch(RuntimeException e){
            if(e.getMessage().contains("busTrip"))
                model.addAttribute("errorMessage", "Bus trip not found");
            else
                model.addAttribute("errorMessage", "Passenger id not found");

            return "ticket/form";
        }

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
    public String update(@PathVariable Long id,
                         @RequestParam(required = false) Long busTripId,
                         @RequestParam(required = false) Long passengerId,
                         @Valid @ModelAttribute("ticket") Ticket ticket,
                         BindingResult bindingResult,
                         Model model) {

        if(busTripId == null){
            model.addAttribute("errorMessage", "Bus trip required");
            return "ticket/form";
        }

        if(passengerId == null){
            model.addAttribute("errorMessage", "Passenger required");
            return "ticket/form";
        }

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e->model.addAttribute("errorMessage", e.getDefaultMessage()));
            return "ticket/form";
        }

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
