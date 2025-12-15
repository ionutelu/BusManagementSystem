package com.example.busstation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@RequestMapping("/menu")
@Controller
public class HelloController {

    @GetMapping
    public String menu() {
        return "menu/index"; // src/main/resources/templates/menu/index.html
    }

    @GetMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Die Anwendung funktioniert!";
    }
}


