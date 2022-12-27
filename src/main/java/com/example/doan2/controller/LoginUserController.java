package com.example.doan2.controller;

import com.example.doan2.entity.User;
import com.example.doan2.entity.UserWallet;
import com.example.doan2.repository.UserWalletRepository;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.MarketService;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import org.web3j.crypto.CipherException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

@Controller
public class LoginUserController {

    @Autowired
    private UserWalletRepository userWalletRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MarketService marketService;

    @Autowired
    private ToadIngameService toadIngameService;

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
    public String showRegisterForm() {
        return "registerMarket";
    }


    @PostMapping("/process_register")
    public String processRegistration(@RequestParam(value = "username", required = false) String username,
                                      @RequestParam(value = "password", required = false) String password,
                                      @RequestParam(value = "confirm_password", required = false) String confirm_password,
                                      @RequestParam(value = "email", required = false) String email,
                                      User user,
                                      Model model) throws InvalidAlgorithmParameterException, CipherException, NoSuchAlgorithmException, NoSuchProviderException {
        if (!userService.checkUserName(username)) {
            model.addAttribute("errorMessageU", "Username already existed, please specify another");
            return "registerMarket";
        } else if (username.trim().equals("") || username.length() < 5 || username.length() > 20) {
            model.addAttribute("errorMessageU", "User name not null! Only Allow (5-20) Characters");
            return "registerMarket";
        } else if (!password.equals(confirm_password)) {
            model.addAttribute("errorMessage", "Password doesn't match");
            return "registerMarket";
        } else if (password.trim().equals("")) {
            model.addAttribute("errorMessage", "Password must not be empty");
            return "registerMarket";
        } else if (!userService.checkEmail(email)) {
            model.addAttribute("errorMessage", "Email already existed, please specify another");
            return "registerMarket";
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String encoderPassword = encoder.encode(user.getPassword());

            user.setName(username);
            user.setEmail(email);
            user.setPassword(encoderPassword);

            UserWallet userWallet = new UserWallet();

            user.setUserWallet(userWallet);
            userWallet.setUser(user);
            userService.createUser(user);
        }
        return "loginMarket";
    }

    @GetMapping("/game")
    public String game(HttpServletResponse response) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        User user = ((UserServiceImp) authentication.getPrincipal()).getUser();
//        Cookie cookie = new Cookie("userId", user.getId().toString());
//        response.addCookie(cookie);
//
//        RedirectView redirectView = new RedirectView();
//        redirectView.setUrl("http://localhost:8000");
//        return redirectView;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) authentication.getPrincipal()).getUser();
        Cookie cookie = new Cookie("userId", user.getId().toString());
        response.addCookie(cookie);


        return "game";
    }

    @GetMapping("/game1")
    public String viewGamePage(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) authentication.getPrincipal()).getUser();
        Cookie cookie = new Cookie("userId", user.getId().toString());
        response.addCookie(cookie);


        return "game";
    }

}
