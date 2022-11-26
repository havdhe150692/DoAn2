package com.example.doan2.controller;

import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MyToadCategoryController {


    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/myToad")
    public String showMyToad(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        List<ToadIngame> myToadLists = toadIngameRepository.findAllToadByOwner(user);
        if (myToadLists.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "myToadCategory";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("myToadList", myToadLists);
        }
        return "myToadCategory";
    }

}
