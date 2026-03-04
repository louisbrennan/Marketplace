package com.example.marketplace.controller;


import com.example.marketplace.model.Basket;
import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.BasketRepository;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ProductRepository;

import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.BasketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.example.marketplace.constant.Genres.F_GENRES;
import static com.example.marketplace.constant.Genres.NF_GENRES;


@Controller
public class ProductPageController {
    private final ProductRepository productRepository;
    private final BasketService basketService;
    private final UserRepository userRepository;
    private final ImageRepository imageRepository;

    public ProductPageController(ProductRepository productRepository,
                                 BasketService basketService,
                                 UserRepository userRepository,
                                 ImageRepository imageRepository) {
        this.basketService = basketService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.imageRepository = imageRepository;
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

    @GetMapping("/product/{id}/edit")
    public String editProduct(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        }
        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        Product product = productRepository.findById(id).orElse(null);
        model.addAttribute("product", product);
        model.addAttribute("f_genres", F_GENRES);
        model.addAttribute("nf_genres", NF_GENRES);
        return "editProduct";
    }

    @PostMapping("/product/{id}/edit")
    public String makeProductChanges(HttpSession session,
                                     @PathVariable Long id, Model model,
                                     @ModelAttribute Product newProduct,
                                     @RequestParam(required = false) MultipartFile imageFile) {
        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        }
        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }
        Product existing = productRepository.findById(id).orElse(null);
        if (existing == null) {
            return "redirect:/";
        }

        existing.editProduct(newProduct);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String uploadDir = System.getProperty("user.dir") + "/uploads/images/";
                Path dirPath = Paths.get(uploadDir);
                Files.createDirectories(dirPath);

                String fileName = UUID.randomUUID() + "_" + imageFile.getOriginalFilename();
                Path filePath = dirPath.resolve(fileName);
                Files.write(filePath, imageFile.getBytes());

                Image imageObj = new Image();
                imageObj.setFileName(fileName);
                imageObj.setFilePath(filePath.toString());
                imageObj.setContentType(imageFile.getContentType());

                imageRepository.save(imageObj);
                existing.setImage(imageObj);
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }

        productRepository.save(existing);
        return "redirect:/product/" + id;
    }

}
