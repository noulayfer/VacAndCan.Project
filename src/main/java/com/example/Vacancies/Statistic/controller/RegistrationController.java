package com.example.Vacancies.Statistic.controller;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.example.Vacancies.Statistic.model.User;
import com.example.Vacancies.Statistic.model.VacancyCard;
import com.example.Vacancies.Statistic.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

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
        model.addAttribute("bindingResult", new BeanPropertyBindingResult(new User(), "user"));
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/register")
    public String registration(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        model.addAttribute("bindingResult", bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("user", user);
            return "registration";
        }
        try {
            userDetailsService.loadUserByUsername(user.getUsername());
            bindingResult.rejectValue("username", "error.user", "Username already exists");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("user", user);
            return "registration";
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (userDetailsService.loadUserByEmail(user.getEmail()) != null) {
            bindingResult.rejectValue("email", "error.user", "Email already exists");
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("user", user);
            return "registration";
        }
        user.setRole(User.Authorities.USER);
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userDetailsService.save(user);
        return "redirect:/login?success";
    }

}
