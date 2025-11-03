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

    @GetMapping
    public String index(Model model) {
        model.addAttribute("buses", busService.findAll());
        return "bus/index";
    }

    @GetMapping("/new")
    public String form(Bus bus) {
        return "bus/form";
    }

    @PostMapping
    public String create(Bus bus) {
        busService.save(bus);
        return "redirect:/buses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        busService.deleteById(id);
        return "redirect:/buses";
    }
}
