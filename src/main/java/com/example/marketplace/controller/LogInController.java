package com.example.marketplace.controller;

import com.example.marketplace.exception.IncorrectPasswordException;
import com.example.marketplace.exception.UserNotFoundException;
import com.example.marketplace.model.User;
import com.example.marketplace.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LogInController {
    private final UserService userService;

    public LogInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/logIn")
    public String logIn(Model model) {
        return "logIn";
    }

    @PostMapping("/logIn")
    public String logUserIn(
            @RequestParam String username, @RequestParam String password,
            HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        try {
            User authenticatedUser = userService.authenticateUser(username, password);
            session.setAttribute("user", authenticatedUser);
            return "redirect:/";
        } catch (UserNotFoundException | IncorrectPasswordException e) {
            model.addAttribute("error", e.getMessage());
            return "logIn";
        }

    }

    @GetMapping("/logOut")
    public String logOut(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/";
    }
}
