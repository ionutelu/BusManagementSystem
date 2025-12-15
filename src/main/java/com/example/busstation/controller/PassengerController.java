package com.example.busstation.controller;

import com.example.busstation.model.Passenger;
import com.example.busstation.service.PassengerService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public String index(@RequestParam(required = false) String sortField,
                        @RequestParam(required = false) String sortDirection,
                        Model model) {
        model.addAttribute("passengers", passengerService.findAllSorted(sortField, sortDirection));
        return "passenger/index";
    }

    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("passenger", new Passenger()); // id = null
        return "passenger/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("passenger") Passenger passenger, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(e -> model.addAttribute("errorMessage", e.getDefaultMessage()));
//            model.addAttribute("route", new Route());
//            model.addAttribute("errorMessage", "Distance must be greater than 1");
            return "passenger/form";
        }
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
    public String update( @PathVariable Long id, @Valid @ModelAttribute("passenger") Passenger passenger, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(e -> model.addAttribute("errorMessage", e.getDefaultMessage()));
//            model.addAttribute("route", new Route());
//            model.addAttribute("errorMessage", "Distance must be greater than 1");
            return "passenger/form";
        }

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
