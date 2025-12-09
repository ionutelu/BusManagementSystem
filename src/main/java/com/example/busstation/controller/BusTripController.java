package com.example.busstation.controller;

import com.example.busstation.model.BusTrip;
import com.example.busstation.service.BusTripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/busTrips")
public class BusTripController {

    private final BusTripService busTripService;

    public BusTripController(BusTripService busTripService) {
        this.busTripService = busTripService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("busTrips", busTripService.findAll());
        return "busTrip/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        return "busTrip/form";
    }

    @PostMapping
    public String create(@ModelAttribute BusTrip busTrip) {
        busTripService.save(busTrip);
        return "redirect:/busTrips";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        busTripService.deleteById(id);
        return "redirect:/busTrips";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("busTrip", busTripService.findById(id));
        return "busTrip/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute BusTrip busTrip) {

        return "redirect:/busTrips";
    }

    @GetMapping("/{id}/details")
    public String viewDetails(@PathVariable Long id, Model model) {

        BusTrip busTrip = busTripService.findById(id);
        model.addAttribute("busTrip", busTrip);
        model.addAttribute("tickets", busTrip.getTickets());
        model.addAttribute("assignments", busTrip.getAssignments());

        return "busTrip/details";
    }

}



