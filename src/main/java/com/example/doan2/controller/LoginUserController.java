package com.example.doan2.controller;

import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginUserController {
    @Autowired
    private UserRepository repo;

    @GetMapping("")
    public String viewTest() {
        return "loginMarket";
    }

    @GetMapping("/loginMarket")
    public String loginPage() {
        return "loginMarket";
    }

    @GetMapping("/registerCheck")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registerMarket";
    }


    @PostMapping("/process_register")
    public String processRegistration(User user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoderPassword = encoder.encode(user.getPassword());
        user.setPassword(encoderPassword);
        repo.save(user);
        return "loginMarket";
    }
    @GetMapping("/market")
    public String viewMarket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        return "market";
    }
}
