package com.example.busstation.controller;

import com.example.busstation.model.Passenger;
import com.example.busstation.service.PassengerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("passengers", passengerService.findAll());
        return "passenger/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("passenger", new Passenger()); // id = null
        return "passenger/form";
    }

    @PostMapping
    public String create(@ModelAttribute Passenger passenger) {
        passengerService.save(passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        passengerService.deleteById(id);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("passenger", passengerService.findById(id));
        return "passenger/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @ModelAttribute Passenger passenger) {
        Passenger existing = passengerService.findById(id);

        existing.setName(passenger.getName());
        existing.setCurrency(passenger.getCurrency());

        passengerService.save(existing);

        return "redirect:/passengers";
    }

    @GetMapping("/{id}/tickets")
    public String viewTickets(@PathVariable Long id, Model model) {
        Passenger passenger = passengerService.findById(id);
        model.addAttribute("passenger", passenger);
        model.addAttribute("tickets", passenger.getTickets());
        return "passenger/tickets";
    }

}
