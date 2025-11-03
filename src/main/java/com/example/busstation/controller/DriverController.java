package com.example.busstation.controller;
import com.example.busstation.model.Driver;
import com.example.busstation.service.DriverService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
