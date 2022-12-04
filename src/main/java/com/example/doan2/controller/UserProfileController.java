package com.example.doan2.controller;

import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.UserService;
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

import java.util.List;

@Controller
public class UserProfileController {

    @Autowired
    UserService userService;

    @Autowired
    ToadIngameService toadIngameService;

    @GetMapping("/checkUserProfile")
    public String checkUserProfile(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        } else {
            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);
        }
        return "checkUserProfile";
    }

    @PostMapping("/changeUserProfile")
    public  String changeUserName(@RequestParam(value = "name", required = false) String username,
                                  Model model,
                                  User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = ((UserServiceImp) auth.getPrincipal()).getUser();
        if(username.trim().length() == 0) {
            model.addAttribute("errorM1","Your name Not Allow nulls");
            return "userProfile";
        } else if (!userService.checkUserName(username)) {
            model.addAttribute("errorMessageU", "Username already existed, please specify another");
            return "userProfile";
        } else {
            user.setName(username);
            userService.updateUser(user);
            model.addAttribute("user",user);
        }
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "userProfile";
    }


    @PostMapping("/accessUserProfile")
    public String verifyPassword(@RequestParam(value = "password", required = false) String password,
                                 Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        Object rawPassword = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        String comparePass = (String) rawPassword;
         if(!password.equals(comparePass)) {
            model.addAttribute("errorM","Your password is wrong , please check again!");
            return "checkUserProfile";
        }
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        model.addAttribute("user",user);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "userProfile";
    }

    @PostMapping("/changeUserPassword")
    public String changeUserPassword(@RequestParam(value = "password", required = false) String password,
                                     @RequestParam(value = "confirm_password", required = false) String re_password,
                                     Model model,
                                     User user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        user = ((UserServiceImp) auth.getPrincipal()).getUser();
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
            userService.updateUser(user);
            model.addAttribute("user",user);
            model.addAttribute("successful","You have Changed your Password Successfully!");
        }
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "userProfile";
    }


}
