package com.example.busstation.controller;

import com.example.busstation.model.Route;
import com.example.busstation.model.Staff;
import com.example.busstation.model.TripManager;
import com.example.busstation.service.RouteService;
import com.example.busstation.service.StaffService;
import com.example.busstation.service.TripManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tripManagers")
public class TripManagerController {

    private final TripManagerService tripManagerService;

    public TripManagerController(TripManagerService tripManagerService) {
        this.tripManagerService = tripManagerService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tripManagers", tripManagerService.findAll());
        return "tripManager/index";
    }

    @GetMapping("/new")
    public String form(TripManager tripManager) {
        return "tripManager/form";
    }

    @PostMapping
    public String create(@ModelAttribute TripManager tripManager) {
        tripManagerService.save(tripManager);
        return "redirect:/tripManagers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        tripManagerService.deleteById(id);
        return "redirect:/tripManagers";
    }
}
