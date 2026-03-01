package com.example.marketplace.controller;

import com.example.marketplace.model.*;
import com.example.marketplace.repository.OrderRepository;
import com.example.marketplace.repository.ProductRepository;
import com.example.marketplace.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.query.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
public class BasketController {
    private final BasketService basketService;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public BasketController(BasketService basketService,
                            ProductRepository productRepository,
                            OrderRepository orderRepository) {
        this.basketService = basketService;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @GetMapping({"/basket/", "/basket"})
    public String basket(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/logIn";
        }

        Basket basket = basketService.getBasket(user);
        double total = 0.0;
        for (BasketItem item : basket.getItems()) {
            total += item.getQuantity() * item.getProduct().getPrice();
        }
        model.addAttribute("total", total);
        model.addAttribute("basket", basket.getItems());
        return "basket";
    }

    @PostMapping("/basket/add/{id}")
    public String addToBasket(HttpSession session, @PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        } else {
            User user = (User) session.getAttribute("user");
            basketService.addToBasket(product, user);
        }
        return "redirect:/basket" ;
    }

    @PostMapping("/basket/remove/{id}")
    public String removeFromBasket(HttpSession session, @PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        } else {
            User user = (User) session.getAttribute("user");
            basketService.removeFromBasket(product, user);
        }

        return "redirect:/basket" ;
    }

    @PostMapping("/basket/delete/{id}")
    public String deleteFromBasket(HttpSession session, @PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        } else {
            User user = (User) session.getAttribute("user");
            basketService.removeAllFromBasket(product, user);
        }
        return "redirect:/basket" ;
    }

    @PostMapping("/checkout")
    public String checkout(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        }
        User user = (User) session.getAttribute("user");
        basketService.basketToOrder(user);
        basketService.clearBasket(user);
        return "redirect:/orderHistory";
    }
}
