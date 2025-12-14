package com.example.busstation.controller;

import com.example.busstation.model.BusStation;
import com.example.busstation.model.BusTrip;
import com.example.busstation.model.Passenger;
import com.example.busstation.model.Route;
import com.example.busstation.service.BusStationService;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.RouteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.RouteMatcher;
import org.springframework.validation.BindingResult;
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
        Route route = new Route();
        model.addAttribute("route", route);
        return "route/form";
    }


//    @PostMapping
//    public String create(@ModelAttribute Route route) {
//        routeService.save(route);
//        return "redirect:/routes";
//
//    }

    @PostMapping
    public String create(@Valid @ModelAttribute Route route, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(e -> model.addAttribute("errorMessage", e.getDefaultMessage()));
//            model.addAttribute("route", new Route());
//            model.addAttribute("errorMessage", "Distance must be greater than 1");
            return "route/form";
        }
        routeService.save(route);
        return "redirect:/routes";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        routeService.deleteById(id);
        return "redirect:/routes";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Route route = routeService.findById(id);
        model.addAttribute("route", route);
        return "route/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id, @Valid @ModelAttribute("route") Route route, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors()
                    .forEach(e -> model.addAttribute("errorMessage", e.getDefaultMessage()));
//            model.addAttribute("route", new Route());
//            model.addAttribute("errorMessage", "Distance must be greater than 1");
            return "route/form";
        }

        Route existing = routeService.findById(id);

        existing.setOrigin(route.getOrigin());
        existing.setDestination(route.getDestination());
        existing.setDistance(route.getDistance());

        routeService.save(existing);

        return "redirect:/routes";
    }

    @GetMapping("/{id}/trips")
    public String viewTrips(@PathVariable Long id, Model model) {
        Route route = routeService.findById(id);
        model.addAttribute("route", route);
        model.addAttribute("busTrips", route.getTrips());
        return "route/busTrips";
    }


}
