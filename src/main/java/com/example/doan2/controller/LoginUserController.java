package com.example.doan2.controller;

import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.CheckExistedLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginUserController {
    @Autowired
    private UserRepository repo;

    @Autowired
    CheckExistedLoginService lcs;

    @GetMapping("")
    public String viewStart() {
        return "Homepage";
    }

    @GetMapping("/Homepage")
    public String viewHome() {
        return "Homepage";
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
    public String processRegistration(@RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "confirm_password", required = false) String confirm_password,
                                      @RequestParam(value = "email", required = false) String email,
                                      User user,
                                      Model model) {
        if (!lcs.checkUserName(username)) {
            model.addAttribute("errorMessageU", "Username already existed, please specify another");
            return "registerMarket";
        }
        if (!password.equals(confirm_password)) {
            model.addAttribute("errorMessage", "Password doesn't match");
            return "registerMarket";
        } else if (password.trim().equals("")) {
            model.addAttribute("errorMessage", "Password must not be empty");
            return "registerMarket";
        } else if (!lcs.checkEmail(email)) {
            model.addAttribute("errorMessage", "Email already existed, please specify another");
            return "registerMarket";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoderPassword = encoder.encode(user.getPassword());
            user.setName(username);
            user.setEmail(email);
            user.setPassword(encoderPassword);
            repo.save(user);
        }
        return "loginMarket";
    }

    @GetMapping("/market")
    public String viewMarket() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        return "market";
    }


}
