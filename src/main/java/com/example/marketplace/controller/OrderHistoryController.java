package com.example.marketplace.controller;

import com.example.marketplace.model.Basket;
import com.example.marketplace.model.OrderItem;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.OrderRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class OrderHistoryController {
    private final OrderRepository orderRepository;

    public OrderHistoryController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @GetMapping("/orderHistory")
    public String orderHistory(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        }
        User user = (User) session.getAttribute("user");
        List<OrderItem> orders = orderRepository.findByUser(user).orElse(new ArrayList<>());

        Map<ZonedDateTime, List<OrderItem>> groupedOrders = orders.stream()
                .sorted(Comparator.comparing(item -> item.getDateTime(), Comparator.reverseOrder()))
                .collect(Collectors.groupingBy(
                        item -> item.getDateTime().truncatedTo(ChronoUnit.MINUTES),
                        LinkedHashMap::new,
                        Collectors.toList()
                ));

        model.addAttribute("orders", groupedOrders);

        return "orderHistory";
    }
}
