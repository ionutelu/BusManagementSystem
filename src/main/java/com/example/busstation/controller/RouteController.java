package com.example.busstation.controller;

import com.example.busstation.model.Route;
import com.example.busstation.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/routes")
public class RouteController {

    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public String listRoutes(Model model) {
        model.addAttribute("routes", routeService.findAll());
        return "route/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("route", new Route());
        return "route/form";
    }

    @PostMapping
    public String addRoute(@ModelAttribute Route route) {
        routeService.save(route);
        return "redirect:/routes";
    }

    @PostMapping("/{id}/delete")
    public String deleteRoute(@PathVariable String id) {
        routeService.deleteById(id);
        return "redirect:/routes";
    }
}
