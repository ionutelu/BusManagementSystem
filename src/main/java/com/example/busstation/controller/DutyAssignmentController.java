package com.example.busstation.controller;


import com.example.busstation.model.BusTrip;
import com.example.busstation.model.DriverRole;
import com.example.busstation.model.DutyAssignment;
import com.example.busstation.model.Staff;
import com.example.busstation.service.BusTripService;
import com.example.busstation.service.DutyAssignmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/assignments")
public class DutyAssignmentController {

    private final DutyAssignmentService dutyAssignmentService;
    private final BusTripService busTripService;

    public DutyAssignmentController(DutyAssignmentService dutyAssignmentService, BusTripService busTripService) {
        this.dutyAssignmentService = dutyAssignmentService;
        this.busTripService = busTripService;
    }

//    @GetMapping
//    public String index(Model model) {
//        model.addAttribute("assignments", dutyAssignmentService.findAll());
//        return "dutyAssignment/index";
//    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String sortField,
            @RequestParam(required = false) String sortDirection,
            Model model
    ) {
        model.addAttribute(
                "assignments",
                dutyAssignmentService.findAllSorted(sortField, sortDirection)
        );
        return "dutyAssignment/index";
    }


    @GetMapping("/new")
    public String form(Model model) {
        model.addAttribute("dutyAssignment", new DutyAssignment());
        return "dutyAssignment/form";
    }

//    @PostMapping
//    public String create(@ModelAttribute DutyAssignment dutyAssignment) {
//        dutyAssignmentService.save(dutyAssignment);
//        return "redirect:/assignments";
//    }

    @PostMapping
    public String create(@RequestParam(required = false) Long tripId,
                         @RequestParam(required = false) Long staffId,
                         @ModelAttribute DutyAssignment dutyAssignment, Model model) {

        if(tripId == null){
            model.addAttribute("errorMessage", "Bus trip required");
            return "dutyAssignment/form";
        }

        if(staffId == null){
            model.addAttribute("errorMessage", "Staff required");
            return "dutyAssignment/form";
        }

        try{
            BusTrip trip = busTripService.findById(tripId);
            Staff staff = new Staff() {};

            staff.setId(staffId);
            dutyAssignment.setTripId(trip);
            dutyAssignment.setStaff(staff);

            dutyAssignmentService.save(dutyAssignment);
        }catch(RuntimeException e){

            if(e.getMessage().contains("busTrip"))
                model.addAttribute("errorMessage", "Bus trip not found");
            else
                model.addAttribute("errorMessage", "Staff id not found");

            return "dutyAssignment/form";
        }


        return "redirect:/assignments";
    }


    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        dutyAssignmentService.deleteById(id);
        return "redirect:/assignments";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        model.addAttribute("dutyAssignment", dutyAssignmentService.findById(id));
        return "dutyAssignment/form";
    }

//    @PostMapping("/{id}")
//    public String update(@PathVariable Long id, @ModelAttribute DutyAssignment dutyAssignment) {
//        DutyAssignment existing = dutyAssignmentService.findById(id);
//
//        existing.getBusTrip();
//        existing.setRole(dutyAssignment.getRole());
//        return "redirect:/assignments";
//    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam Long tripId,
                         @RequestParam Long staffId,
                         @ModelAttribute DutyAssignment dutyAssignment) {

        DutyAssignment existing = dutyAssignmentService.findById(id);

        // SETARE TRIP
        BusTrip trip = busTripService.findById(tripId);
        existing.setTripId(trip);

        // SETARE STAFF (proxy cu doar id)
        Staff staff = new Staff() {};
        staff.setId(staffId);
        existing.setStaff(staff);

        // SETARE ROLE
        existing.setRole(dutyAssignment.getRole());

        dutyAssignmentService.save(existing);

        return "redirect:/assignments";
    }

}
