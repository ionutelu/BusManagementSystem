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
    public String index(Model model) {
        model.addAttribute("routes", routeService.findAll());
        return "route/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("route", new Route());
        return "route/form";
    }

    @PostMapping
    public String create(@ModelAttribute Route route) {
        routeService.save(route);
        return "redirect:/routes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        routeService.deleteById(id);
        return "redirect:/routes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("route", routeService.findById(id));
        return "route/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Route route) {

        return "redirect:/routes";
    }
}
