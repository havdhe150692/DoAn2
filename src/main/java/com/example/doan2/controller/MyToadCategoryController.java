package com.example.doan2.controller;

import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.ToadIngameService;
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
    ToadIngameService toadIngameService;


    @GetMapping("/myToad")
    public String showMyToad(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        List<ToadIngame> myToadLists = toadIngameService.findAllToadByOwner(user);
        if (myToadLists.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "myToadCategory";
        } else {
            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("myToadList", myToadLists);
        }
        return "myToadCategory";
    }

}
