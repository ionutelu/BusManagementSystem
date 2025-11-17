package com.example.busstation.controller;

import com.example.busstation.model.BusTrip;
import com.example.busstation.service.BusTripService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/busTrips")
public class BusTripController {

    private final BusTripService busTripService;

    public BusTripController(BusTripService busTripService) {
        this.busTripService = busTripService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        binder.registerCustomEditor(LocalDateTime.class, new PropertyEditorSupport() {
            @Override public void setAsText(String text) {
                setValue((text == null || text.isBlank()) ? null : LocalDateTime.parse(text, f));
            }
            @Override public String getAsText() {
                LocalDateTime v = (LocalDateTime) getValue();
                return v == null ? "" : v.format(f);
            }
        });
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("busTrips", busTripService.findAll());
        return "busTrip/index";
    }

    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("busTrip", new BusTrip());
        return "busTrip/form";
    }

    @PostMapping
    public String create(@ModelAttribute BusTrip busTrip) {
        busTripService.save(busTrip);
        return "redirect:/busTrips";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        busTripService.deleteById(id);
        return "redirect:/busTrips";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("busTrip", busTripService.findById(id));
        return "busTrip/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute BusTrip busTrip) {
        busTripService.save(busTrip);
        return "redirect:/busTrips";
    }
}
