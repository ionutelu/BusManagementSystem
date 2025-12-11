package com.example.busstation.exception;

import com.example.busstation.model.Bus;
import com.example.busstation.model.BusStation;
import org.springframework.ui.Model;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    // handler pentru erorile de binding (enum invalid, conversii eșuate, valori invalide)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String handleBindingErrors(MethodArgumentNotValidException ex, Model model) {

        Object target = ex.getBindingResult().getTarget(); // modelul pentru care a eșuat bindingul

        // tratare pentru excepțiile custom aruncate din setteri (ex: *Invalid)
        Throwable cause = ex.getCause();
        if (cause != null) {
            Throwable root = getRootCause(cause);

            if (root.getClass().getSimpleName().endsWith("Invalid")) {
                model.addAttribute("errorMessage", root.getMessage());
                model.addAttribute(getModelName(target), target);
                return getFormView(target);
            }
        }

        // tratare pentru FieldError generat de Spring (ex: conversii nereușite)
        if (!ex.getBindingResult().getFieldErrors().isEmpty()) {
            var fieldError = ex.getBindingResult().getFieldErrors().get(0);
            String message = fieldError.getDefaultMessage();

            model.addAttribute("errorMessage", message);
            model.addAttribute(getModelName(target), target);
            return getFormView(target);
        }

        // fallback general
        model.addAttribute("errorMessage", "Invalid input.");
        model.addAttribute(getModelName(target), target);
        return getFormView(target);
    }

    // handler pentru exceptiile legate de Bus (VIN duplicat, număr înmatriculare duplicat, capacitate invalidă)
    @ExceptionHandler({
            DuplicateRegistrationException.class,
            DuplicateVinException.class,
            BusCapacityInvalid.class
    })
    public String handleBusErrors(RuntimeException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("bus", new Bus());
        return "bus/form";
    }

    // handler pentru situațiile în care un Bus nu este găsit
    @ExceptionHandler(BusNotFoundException.class)
    public String handleBusNotFound(BusNotFoundException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("bus", new Bus());
        return "bus/form";
    }

    // handler pentru stații duplicate
    @ExceptionHandler(DuplicateBusStationException.class)
    public String handleDuplicateBusStationException(DuplicateBusStationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("busStation", new BusStation());
        return "busStation/form";
    }

    // handler pentru stație invalidă în contextul rutelor
    @ExceptionHandler(InvalidStationException.class)
    public String handleInvalidStationException(InvalidStationException ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        model.addAttribute("route", new BusStation());
        return "route/form";
    }

    // handler pentru cazul în care o rută nu este găsită
    @ExceptionHandler(RouteNotFoundException.class)
    public String handleRouteNotFound(RouteNotFoundException ex, Model model) {
        return "redirect:/routes?error=" + ex.getMessage();
    }

    // extrage cauza finală a unei excepții
    private Throwable getRootCause(Throwable ex) {
        while (ex.getCause() != null) ex = ex.getCause();
        return ex;
    }

    // returnează numele obiectului model în formatul folosit în thymeleaf
    private String getModelName(Object o) {
        String simple = o.getClass().getSimpleName();
        return Character.toLowerCase(simple.charAt(0)) + simple.substring(1);
    }

    // returnează view-ul corect al formularului aferent modelului
    private String getFormView(Object o) {
        return getModelName(o) + "/form";
    }
}
