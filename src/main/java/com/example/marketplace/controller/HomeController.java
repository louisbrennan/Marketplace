package com.example.marketplace.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(Model model) {
        return "index";
    }
}
