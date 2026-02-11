package com.example.marketplace.controller;

import com.example.marketplace.repository.ProductRepository;

public class ProductController {
    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
}
