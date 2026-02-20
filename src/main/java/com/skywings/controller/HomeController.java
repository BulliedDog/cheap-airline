package com.skywings.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(Model model) {
        //model.checkAttribut()

        model.addAttribute("pageTitle", "Home - SkyWings Airlines");
        return "index";
    }
}
