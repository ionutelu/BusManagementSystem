package com.example.busstation.controller;

import com.example.busstation.model.Driver;
import com.example.busstation.service.DriverService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/drivers")

public class DriverController {
    private final DriverService driverService;
    public DriverController(DriverService driverService){
        this.driverService = driverService;
    }

//    @GetMapping
//    public String index(Model model){
//        model.addAttribute("drivers", driverService.findAll());
//        return "driver/index";
//    }

//    @GetMapping
//    public String index(
//            @RequestParam(required = false) String sortField,
//            @RequestParam(required = false) String sortDirection,
//            Model model
//    ) {
//        model.addAttribute(
//                "drivers",
//                driverService.findAllSorted(sortField, sortDirection)
//        );
//        return "driver/index";
//    }

    @GetMapping
    public String index(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Integer minExperience,
            @RequestParam(required = false, defaultValue = "id") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDirection,
            Model model
    ) {
        model.addAttribute(
                "drivers",
                driverService.findFilteredAndSorted(
                        name, minExperience, sortField, sortDirection
                )
        );
        return "driver/index";
    }


    @GetMapping("/new")
    public String form(Driver driver) {
        return "driver/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("driver") Driver driver, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(e->model.addAttribute("errorMessage", e.getDefaultMessage()));
            return "driver/form";
        }
        driverService.save(driver);
        return "redirect:/drivers";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable long id) {
        driverService.deleteById(id);
        return "redirect:/drivers";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("driver", driverService.findById(id));
        return "driver/form";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable long id, @ModelAttribute Driver driver) {
        driver.setId(id);
        driverService.save(driver);
        return "redirect:/drivers";
    }



}
