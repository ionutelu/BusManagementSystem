package com.example.busstation.controller;

import com.example.busstation.model.Bus;
import com.example.busstation.service.BusService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/buses")
public class BusController {

    private final BusService busService;

    public BusController(BusService busService) {
        this.busService = busService;
    }

    // GET /buses -> afișează lista de autobuze
    @GetMapping
    public String listBuses(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "bus/index"; // încarcă templates/bus/index.html
    }

    @GetMapping("/new")
    public String showForm(Bus bus) {
        return "bus/form"; // încarcă templates/bus/form.html
    }

    // POST /buses -> salvează autobuzul nou
    @PostMapping
    public String addBus(Bus bus) {
        busService.save(bus);
        return "redirect:/buses"; // redirect către lista de autobuze
    }

    // POST /buses/{id}/delete -> șterge autobuzul cu ID-ul dat
    @PostMapping("/{id}/delete")
    public String deleteBus(@PathVariable String id) {
        busService.deleteById(id);
        return "redirect:/buses";
    }
}
