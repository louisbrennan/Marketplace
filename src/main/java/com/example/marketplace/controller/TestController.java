package com.example.marketplace.controller;

import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    private final ProductRepository productRepository;

    public TestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/test")
    public String testDB() {
        Product product = new Product();
        product.setTitle("Test");
        product.setAuthor("Test McTest");
        product.setPublisher("Test Publisher");
        product.setGenre("Testing");
        product.setDescription("A test book written as a test for testing purposes. Written by Test McTest and pubished by Test publisher LTD, it made waves in the testing world.");
        product.setPrice(10.10);
        productRepository.save(product);
        return "test";
    }
}
