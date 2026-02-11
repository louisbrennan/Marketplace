package com.example.marketplace.controller;

import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ProductRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Controller
public class ProductPageController {
    private final ProductRepository productRepository;

    public ProductPageController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/product/{id}")
    public String product(@PathVariable Long id, Model model) throws MalformedURLException {
        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);

        return "product";
    }

}
