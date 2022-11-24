package com.example.doan2.controller;

import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.CheckExistedLoginService;
import com.example.doan2.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserProfileController {

    @Autowired
    CheckExistedLoginService lcs;

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/checkUserProfile")
    public String checkUserProfile() {
        return "checkUserProfile";
    }

    @PostMapping("/changeUserProfile")
    public  String changeUserName(@RequestParam(value = "name", required = false) String username,
                                  Model model,
                                  User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = ((UserLoginService) auth.getPrincipal()).getUser();
        if(username.trim().length() == 0) {
            model.addAttribute("errorM1","Your name Not Allow nulls");
            return "userProfile";
        } else if (!lcs.checkUserName(username)) {
            model.addAttribute("errorMessageU", "Username already existed, please specify another");
            return "userProfile";
        } else {
            user.setName(username);
            userRepo.save(user);
            model.addAttribute("user",user);
        }
        return "userProfile";
    }


    @PostMapping("/accessUserProfile")
    public String verifyPassword(@RequestParam(value = "password", required = false) String password,
                                 Model model) {
        Object rawPassword = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String comparePass = (String) rawPassword;
         if(!password.equals(comparePass)) {
            model.addAttribute("errorM","Your password is wrong , please check again!");
            return "checkUserProfile";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        model.addAttribute("user",user);
        return "userProfile";
    }

    @PostMapping("/changeUserPassword")
    public String changeUserPassword(@RequestParam(value = "password", required = false) String password,
                                     @RequestParam(value = "confirm_password", required = false) String re_password,
                                     Model model,
                                     User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = ((UserLoginService) auth.getPrincipal()).getUser();
        Object rawPassword = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String comparePass = (String) rawPassword;
        if(password.equals(comparePass)) {
            model.addAttribute("oldPass","This is your old password!");
            return "userProfile";
        }
        if(password.trim().length() == 0 || re_password.trim().length() == 0) {
            model.addAttribute("passwordUserNN","Password not null!");
            return "userProfile";
        } else if(!password.trim().equals(re_password.trim())) {
            model.addAttribute("passwordM","password must matching");
            return "userProfile";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoderPassword = encoder.encode(password);
            user.setPassword(encoderPassword);
            userRepo.save(user);
            model.addAttribute("user",user);
            model.addAttribute("successful","You have Changed your Password Successfully!");
        }
        return "userProfile";
    }


}
