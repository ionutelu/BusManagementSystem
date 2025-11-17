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

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model){
        Bus bus = busService.findById(id);
        model.addAttribute("bus", bus);
        return "bus/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, Bus bus) {
        busService.update(bus, id);
        return "redirect:/buses";
    }

}
