package com.example.marketplace.controller;

import com.example.marketplace.exception.EmailAlreadyExistsException;
import com.example.marketplace.exception.UsernameAlreadyExistsException;
import com.example.marketplace.model.User;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.service.UserService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/signUp")
    public String signUp(Model model) {
        return "signUp";
    }

    @PostMapping("/signUp")
    public String createUser(@ModelAttribute User user, Model model) {
        String username = user.getUsername();

        try {
            userService.registerUser(user);
        } catch (UsernameAlreadyExistsException ex) {
            model.addAttribute("error", "Username already taken");
        } catch (EmailAlreadyExistsException ex) {
            model.addAttribute("error", "Email already registered");
        }

        return "signUp";
    }
}
