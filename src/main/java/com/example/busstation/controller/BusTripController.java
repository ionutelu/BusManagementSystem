package com.example.busstation.controller;

import com.example.busstation.exception.BusNotFoundForTripException;
import com.example.busstation.exception.EmptyFieldException;
import com.example.busstation.exception.RouteNotFoundForTripException;
import com.example.busstation.model.*;
import com.example.busstation.service.BusService;
import com.example.busstation.service.BusStationService;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.RouteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/busTrips")
public class BusTripController {

    private final BusTripService busTripService;
    private final RouteService routeService;
    private final BusService busService;
    private final BusStationService busStationService;

    public BusTripController(BusTripService busTripService, RouteService routeService,
                             BusService busService, BusStationService busStationService) {
        this.busTripService = busTripService;
        this.routeService = routeService;
        this.busService = busService;
        this.busStationService = busStationService;
    }

//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("busTrips", busTripService.findAll());
//        return "busTrip/index";
//    }

//    @GetMapping
//    public String index(
//            @RequestParam(required = false) String sortField,
//            @RequestParam(required = false) String sortDirection,
//            Model model
//    ) {
//        model.addAttribute(
//                "busTrips",
//                busTripService.findAllSorted(sortField, sortDirection)
//        );
//        return "busTrip/index";
//    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String route,
            @RequestParam(required = false) BusTripStatus status,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            Model model
    ) {
        model.addAttribute(
                "busTrips",
                busTripService.findFilteredAndSorted(
                        route, status, sortField, sortDirection
                )
        );
        return "busTrip/index";
    }




    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        return "busTrip/form";
    }

//    @PostMapping
//    public String create(@ModelAttribute BusTrip busTrip) {
//        busTripService.save(busTrip);
//        return "redirect:/busTrips";
//    }


    @PostMapping
    public String create(@RequestParam (required = false) Long routeId,
                         @RequestParam (required = false) Long busId,
                         @ModelAttribute BusTrip busTrip, Model model) {

        if (routeId == null || busId == null || busTrip.getStartTime() == null) {

            model.addAttribute("errorMessage", "Toate datele sunt necesare!!");
            //model.addAttribute("busTrip", new BusTrip());
            model.addAttribute("routeId", routeId);
            model.addAttribute("busId", busId);
            return "busTrip/form";
            //throw new EmptyFieldException("Route ID și Bus ID are mandatory");
        }
        try{
            Route route = routeService.findById(routeId);
            Bus bus = busService.findById(busId);
            busTrip.setRoute(route);
            busTrip.setBus(bus);
        }catch(RuntimeException e){
            if(e.getMessage().contains("Route"))
                throw new RouteNotFoundForTripException("Route not found");
            if(e.getMessage().contains("Bus"))
                throw new BusNotFoundForTripException("Bus not found");
        }
        busTripService.save(busTrip);
        return "redirect:/busTrips";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        busTripService.deleteById(id);
        return "redirect:/busTrips";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("busTrip", busTripService.findById(id));
        return "busTrip/form";
    }

//    @PostMapping("/{id}")
//    public String update(@PathVariable Long id, @ModelAttribute BusTrip busTrip) {
//
//        BusTrip existing = busTripService.findById(id);
//
//        existing.setBus(busTrip.getBus());
//        existing.setRoute(busTrip.getRoute());
//        existing.setStartTime(busTrip.getStartTime());
//        existing.setStatus(busTrip.getStatus());
//
//        busTripService.save(existing);
//
//        return "redirect:/busTrips";
//    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam(required = false) Long routeId,
                         @RequestParam(required = false) Long busId,
                         @ModelAttribute BusTrip busTrip, Model model) {

        if (routeId == null || busId == null || busTrip.getStartTime() == null) {

            model.addAttribute("errorMessage", "Toate datele sunt necesare!!");
            //model.addAttribute("busTrip", new BusTrip());
            model.addAttribute("routeId", routeId);
            model.addAttribute("busId", busId);
            return "busTrip/form";
            //throw new EmptyFieldException("Route ID și Bus ID are mandatory");
        }


        BusTrip existing = busTripService.findById(id);
        try{
            Route route = routeService.findById(routeId);
            Bus bus = busService.findById(busId);

            existing.setRoute(route);
            existing.setBus(bus);
            existing.setStartTime(busTrip.getStartTime());
            existing.setStatus(busTrip.getStatus());

        }catch(RuntimeException e){
            throw new RouteNotFoundForTripException("Route not found");
        }
        busTripService.save(existing);
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

    @GetMapping("/{id}/addStation")
    public String addStationForm(@PathVariable Long id, Model model) {
        BusTrip trip = busTripService.findById(id);

        model.addAttribute("trip", trip);
        model.addAttribute("stations", busStationService.findAll());

        return "busTrip/addStation";
    }


    @PostMapping("/{tripId}/addStation")
    public String addStation(
            @PathVariable Long tripId,
            @RequestParam Long stationId
    ) {
        BusTrip trip = busTripService.findById(tripId);
        BusStation station = busStationService.findById(stationId);

        trip.getBusStations().add(station);
        station.getTrips().add(trip);

        busTripService.save(trip);

        return "redirect:/busTrips/" + tripId;
    }

    @GetMapping("/{id}")
    public String redirectDetails(@PathVariable Long id) {
        return "redirect:/busTrips/" + id + "/details";
    }

}



