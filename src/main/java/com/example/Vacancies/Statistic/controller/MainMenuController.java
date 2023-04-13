package com.example.Vacancies.Statistic.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainMenuController {
    @GetMapping()
    public String home() {
        return "index";
    }

    @GetMapping("/about-us")
    public String aboutUs() {
        return "about-us";
    }


    @GetMapping("/contacts")
    public String contacts() {
        return "contacts";
    }
}
