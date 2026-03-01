package com.example.marketplace.controller;


import com.example.marketplace.model.Basket;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.BasketRepository;
import com.example.marketplace.repository.ProductRepository;

import com.example.marketplace.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;


@Controller
public class ProductPageController {
    private final ProductRepository productRepository;
    private final BasketService basketService;

    public ProductPageController(ProductRepository productRepository, BasketService basketService) {
        this.basketService = basketService;
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) throws MalformedURLException {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        return "product";
    }

    @PostMapping("/product/{id}/add")
    public String addToBasket(HttpSession session, @PathVariable Long id, Model model) {
        Product product = productRepository.findById(id).orElse(null);

        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        } else {
            User user = (User) session.getAttribute("user");
            basketService.addToBasket(product, user);
        }
        return "redirect:/product/" + id;
    }

}
