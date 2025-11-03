package com.example.busstation.controller;

import com.example.busstation.model.DutyAssignment;
import com.example.busstation.service.DutyAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class DutyAssignmentController {

    private final DutyAssignmentService dutyAssignmentService;

    public DutyAssignmentController(DutyAssignmentService dutyAssignmentService) {
        this.dutyAssignmentService = dutyAssignmentService;
    }

    @GetMapping
    public String listAssignments(Model model) {
        model.addAttribute("assignments", dutyAssignmentService.findAll());
        return "dutyAssignment/index";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("dutyAssignment", new DutyAssignment());
        return "dutyAssignment/form";
    }

    @PostMapping
    public String addDutyAssignments(@ModelAttribute DutyAssignment dutyAssignment) {
        dutyAssignmentService.save(dutyAssignment);
        return "redirect:/assignments";
    }

    @PostMapping("/{id}/delete")
    public String deleteAssignment(@PathVariable String id) {
        dutyAssignmentService.deleteById(id);
        return "redirect:/assignments";
    }
}
