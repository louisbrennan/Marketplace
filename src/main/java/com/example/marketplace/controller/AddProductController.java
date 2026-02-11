package com.example.marketplace.controller;

import com.example.marketplace.model.Image;
import com.example.marketplace.model.Product;
import com.example.marketplace.repository.ImageRepository;
import com.example.marketplace.repository.ProductRepository;
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

@Controller
public class AddProductController {
    private final ProductRepository productRepository;
    private final ImageRepository imageRepository;

    public AddProductController(ProductRepository productRepository, ImageRepository imageRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        return "addProduct";
    }

    @PostMapping("/addProduct")
    public String uploadProduct
            (@ModelAttribute Product product, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
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
            productRepository.save(product);

            return "redirect:/";
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
