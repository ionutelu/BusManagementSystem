package com.example.busstation.exception;

import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStation;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegistrationException.class)
    public String handleDuplicateRegistration(
            DuplicateRegistrationException ex,
            Model model)
    {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("bus", new Bus());

        return "bus/form";
    }

    @ExceptionHandler(DuplicateVinException.class)
    public String handleDuplicateVin(DuplicateVinException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("bus", new Bus());
        return "bus/form";
    }

    @ExceptionHandler(DuplicateBusStationException.class)
    public String handleDuplicateBusStationException(DuplicateBusStationException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("busStation", new BusStation());
        return "busStation/form";
    }

    @ExceptionHandler(InvalidStationException.class)
    public String handleInvalidStationException(InvalidStationException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("route", new BusStation());
        return "route/form";
    }


    @ExceptionHandler(RouteNotFoundException.class)
    public String handleRouteNotFound(RouteNotFoundException ex, Model model){
        model.addAttribute("errorMessage", ex.getMessage());
        //model.addAttribute("routes", routeService.findAll());
        return "redirect:/routes?error=" + ex.getMessage();
    }
}
