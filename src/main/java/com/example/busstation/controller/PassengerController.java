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
        return "passenger/index"; // încarcă templates/passenger/index.html
    }

    @GetMapping("/new")
    public String form(Passenger passenger) {
        return "passenger/form";
    }

    @PostMapping
    public String create(@ModelAttribute Passenger passenger) {
        passengerService.save(passenger);
        return "redirect:/passengers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        passengerService.deleteById(id);
        return "redirect:/passengers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("passenger", passengerService.findById(id));
        return "passenger/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Passenger passenger) {
        passengerService.update(passenger, id);
        return "redirect:/passengers";
    }

}
