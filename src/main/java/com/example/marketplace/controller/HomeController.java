package com.example.marketplace.controller;

import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ProductRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;

import static com.example.marketplace.constant.Genres.F_GENRES;
import static com.example.marketplace.constant.Genres.NF_GENRES;

@Controller
public class HomeController {

    private final ProductRepository productRepository;

    public HomeController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping({"/", "/home", "/index"})
    public String home(Model model) {
        model.addAttribute("products", productRepository.findAll());
        model.addAttribute("f_genres", F_GENRES);
        model.addAttribute("nf_genres", NF_GENRES);
        return "index";
    }

    @GetMapping("/search")
    public String index(Model model, @RequestParam(required = false) String keyword) {
        model.addAttribute("products", productRepository.search(keyword).orElse(null));
        model.addAttribute("f_genres", F_GENRES);
        model.addAttribute("nf_genres", NF_GENRES);
        return "index";
    }

}
