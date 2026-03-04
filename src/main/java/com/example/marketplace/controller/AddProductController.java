package com.example.marketplace.controller;

import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ProductRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static com.example.marketplace.constant.Genres.F_GENRES;
import static com.example.marketplace.constant.Genres.NF_GENRES;

@Controller
public class AddProductController {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public AddProductController(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model, HttpSession session) {
        if (session.getAttribute("user") == null) {
            return "redirect:/logIn";
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }

        model.addAttribute("f_genres", F_GENRES);
        model.addAttribute("nf_genres", NF_GENRES);
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String uploadProduct (@ModelAttribute Product product,
                                 @RequestParam(required = false) MultipartFile imageFile,
                                 HttpSession session) throws IOException {
        if (session.getAttribute("user") == null) {
            return "redirect:/login";
        }

        User user = (User) session.getAttribute("user");
        if (!user.isAdmin()) {
            return "redirect:/";
        }

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
                product.setImage(imageObj);

            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        }
        productRepository.save(product);

        long id = product.getId();
        return "redirect:/product/" + id;
    }
}
