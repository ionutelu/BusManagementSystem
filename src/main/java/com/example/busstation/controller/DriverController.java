package com.example.busstation.controller;

import com.example.busstation.model.Driver;
import com.example.busstation.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")

public class DriverController {
    private final DriverService driverService;
    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

    @GetMapping
    public String index(Model model){
        model.addAttribute("drivers", driverService.findAll());
        return "driver/index";
    }

    @GetMapping("/new")
    public String form(Driver driver) {
        return "driver/form";
    }

    @PostMapping
    public String create(Driver driver) {
        driverService.save(driver);
        return "redirect:/drivers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable String id) {
        driverService.deleteById(id);
        return "redirect:/drivers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable String id, Model model) {
        model.addAttribute("driver", driverService.findById(id));
        return "driver/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable String id, @ModelAttribute Driver driver) {
        driverService.update(driver, id);
        return "redirect:/drivers";
    }
}
