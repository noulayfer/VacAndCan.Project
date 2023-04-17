package com.example.Vacancies.Statistic.controller;

import com.example.Vacancies.Statistic.model.User;
import com.example.Vacancies.Statistic.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/users")
    public String getAllUsers(Model model) {
        List<User> allUsers = userDetailsService.getAll();
        List<User> usersWithUserRole = allUsers.stream()
                .filter(user -> user.getRole().toString().equals("USER"))
                .collect(Collectors.toList());
        model.addAttribute("users", usersWithUserRole);
        return "all-users";
    }


    @DeleteMapping("/users/{id}")
    public String deleteUser(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) {
        userDetailsService.delete(id);
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().startsWith(id)) {
                    cookie.setMaxAge(0);
                    cookie.setValue("");
                    response.addCookie(cookie);
                }
            }
        }
        return "redirect:/admin/users";
    }
}
