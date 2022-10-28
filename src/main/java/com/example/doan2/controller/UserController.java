package com.example.doan2.controller;

import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    @Autowired
    private UserRepository repo;

    @GetMapping("")
    public String viewTest() {
        return "login1";
    }

    @GetMapping("/registerCheck")
    public String showRegisterForm(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "register";
    }


    @PostMapping("/process_register")
    public String processRegistration(User user) {
        repo.save(user);
        return "login1";
    }
}
