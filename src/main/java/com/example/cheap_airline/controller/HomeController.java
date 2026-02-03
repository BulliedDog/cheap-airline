package com.example.cheap_airline.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("pageTitle", "Home - SkyWings Airlines");
        // model.addAttribute("message", "Welcome!"); // Optional: if you want to test flash messages
        return "index";
    }
}
