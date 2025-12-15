package com.example.busstation.controller;

import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStation;
import com.example.busstation.service.BusStationService;
import com.example.busstation.service.DutyAssignmentService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/busStations")
public class BusStationController {
    private final BusStationService busStationService;

    public BusStationController(BusStationService busStationService){
        this.busStationService = busStationService;
    }

//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("busStations", busStationService.findAll());
//        return "busStation/index";
//    }

//    @GetMapping
//    public String index(
//            @RequestParam(required = false) String sortField,
//            @RequestParam(required = false) String sortDirection,
//            Model model
//    ) {
//        model.addAttribute("busStations",
//                busStationService.findAllSorted(sortField, sortDirection));
//        return "busStation/index";
//    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Boolean damaged,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            Model model
    ) {
        model.addAttribute(
                "busStations",
                busStationService.findFilteredAndSorted(
                        name, damaged, sortField, sortDirection
                )
        );
        return "busStation/index";
    }



    @GetMapping("/new")
    public String form(BusStation busStation) {
        return "busStation/form";
    }

//    @PostMapping
//    public String create(BusStation busStation) {
//        busStationService.save(busStation);
//        return "redirect:/busStations";
//    }

    @PostMapping
    public String create(@ModelAttribute BusStation busStation) {

        busStationService.save(busStation);
        return "redirect:/busStations";
    }



    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        busStationService.deleteById(id);
        return "redirect:/busStations";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model){
        BusStation busStation = busStationService.findById(id);
        model.addAttribute("busStation", busStation);
        return "busStation/form";
    }

//    @PostMapping("/{id}")
//    public String update(@PathVariable Long id, @ModelAttribute BusStation busStation) {
//        BusStation existing = busStationService.findById(id);
//
//        existing.setCity(busStation.getCity());
//        existing.setName(busStation.getName());
//        existing.setDamaged(busStation.getDamaged());
//
//        busStationService.save(existing);
//
//        return "redirect:/busStations";
//    }

    @PostMapping("/{id}")
    public String update(
            @PathVariable Long id,
            @Valid @ModelAttribute("busStation") BusStation busStation,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "busStation/form";
        }

        BusStation existing = busStationService.findById(id);

        existing.setName(busStation.getName());
        existing.setCity(busStation.getCity());
        existing.setDamaged(busStation.getDamaged());

        busStationService.save(existing);
        return "redirect:/busStations";
    }



    @GetMapping("/{id}/trips")
    public String viewTrips(@PathVariable Long id, Model model) {

        BusStation busStation = busStationService.findById(id);

        model.addAttribute("station", busStation);
        model.addAttribute("trips", busStation.getTrips());

        return "busStation/trips";  // templates/busStation/trips.html
    }

}
