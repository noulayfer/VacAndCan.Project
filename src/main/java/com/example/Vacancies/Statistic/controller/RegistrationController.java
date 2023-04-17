package com.example.Vacancies.Statistic.controller;

import com.example.Vacancies.Statistic.model.User;
import com.example.Vacancies.Statistic.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String registration(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registration(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/register";
        }
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
            model.addAttribute("error", "Username already exists!");
            System.out.println("eroor username exist");
            return "redirect:/register";
        } catch (Exception e) {
            e.getMessage();
        }

        if (userDetailsService.loadUserByEmail(user.getEmail()) != null) {
            model.addAttribute("error", "Email already exists!");
            return "redirect:/register";
        }
        user.setRole(User.Authorities.USER);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDetailsService.save(user);
        return "redirect:/login?success";
    }

}
