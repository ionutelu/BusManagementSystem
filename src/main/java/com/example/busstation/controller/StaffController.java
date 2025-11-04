package com.example.busstation.controller;

import com.example.busstation.model.Route;
import com.example.busstation.model.Staff;
import com.example.busstation.service.RouteService;
import com.example.busstation.service.StaffService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController {

    private final StaffService staffService;

    public StaffController(StaffService staffService) {
        this.staffService = staffService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("staff", staffService.findAll());
        return "staff/index";
    }

    @GetMapping("/new")
    public String showForm(Staff staff) {
        return "staff/form";
    }

    @PostMapping
    public String addStaff(@ModelAttribute Staff staff) {
        staffService.save(staff);
        return "redirect:/staff";
    }

    @PostMapping("/{id}/delete")
    public String deleteStaff(@PathVariable String id) {
        staffService.deleteById(id);
        return "redirect:/staff";
    }
}
