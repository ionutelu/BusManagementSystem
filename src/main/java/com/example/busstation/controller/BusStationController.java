package com.example.busstation.controller;

import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStation;

import com.example.busstation.service.BusStationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/busStations")
public class BusStationController {
    private final BusStationService busStationService;

    public BusStationController(BusStationService busStationService){
        this.busStationService = busStationService;
    }

    @GetMapping
    public String listBuses(Model model) {
        model.addAttribute("busStations", busStationService.findAll());
        return "busStation/index";
    }

    @GetMapping("/new")
    public String showForm(BusStation busStation) {
        return "busStation/form";
    }

    @PostMapping
    public String addBusStations(BusStation busStation) {
        busStationService.save(busStation);
        return "redirect:/busStations";
    }

    @PostMapping("/{id}/delete")
    public String deleteBusStations(@PathVariable String id) {
        busStationService.deleteById(id);
        return "redirect:/busStations";
    }
}
