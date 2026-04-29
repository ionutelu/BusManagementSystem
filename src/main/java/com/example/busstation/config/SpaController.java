package com.example.busstation.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Forwards all non-API, non-static GET requests to index.html so that
 * React Router can handle client-side navigation in production.
 */
@Controller
public class SpaController {

    @GetMapping(value = {
            "/",
            "/buses",
            "/bus-stations",
            "/routes",
            "/bus-trips",
            "/drivers",
            "/trip-managers",
            "/passengers",
            "/tickets",
            "/assignments",
    })
    public String spa() {
        return "forward:/index.html";
    }
}

