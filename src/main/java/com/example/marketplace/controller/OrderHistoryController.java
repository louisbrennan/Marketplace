package com.example.marketplace.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OrderHistoryController {
    @GetMapping("/orderHistory")
    public String orderHistory(Model model) {
        return "orderHistory";
    }
}
