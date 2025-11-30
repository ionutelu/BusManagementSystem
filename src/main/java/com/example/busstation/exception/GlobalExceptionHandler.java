package com.example.busstation.exception;

import com.example.busstation.model.Bus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateRegistrationException.class)
    public String handleDuplicateRegistration(
            DuplicateRegistrationException ex,
            Model model
    ) {
        model.addAttribute("errorMessage", ex.getMessage());


        model.addAttribute("bus", new Bus());

        return "bus/form";
    }
}
