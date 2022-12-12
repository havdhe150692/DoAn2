package com.example.doan2.controller;

import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.ToadIngameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MyToadCategoryController {


    @Autowired
    ToadIngameService toadIngameService;


    @GetMapping("/myToad")
    public String showToadDetail(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        List<ToadIngame> myToadLists = toadIngameService.findAllToadByOwner(user);
        model.addAttribute("countUserToadSize", toadIngameService.countAllUserToad(user.getId()));
        model.addAttribute("countUserCommonSize", toadIngameService.countUserToadByRarity(user.getId(), 0));
        model.addAttribute("countUserRareSize", toadIngameService.countUserToadByRarity(user.getId(), 1));
        model.addAttribute("countUserEpicSize", toadIngameService.countUserToadByRarity(user.getId(), 2));
        model.addAttribute("countUserMythicalSize", toadIngameService.countUserToadByRarity(user.getId(), 3));
        model.addAttribute("countUserLegendSize", toadIngameService.countUserToadByRarity(user.getId(), 4));
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

    @GetMapping("/findUserToadByRarity/{rarity}")
    public String viewUserToadCommonRarity(@PathVariable("rarity") int rarity, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        List<ToadIngame> listUserToadCommonRarity = toadIngameService.findUserToadByRarity(rarity, user.getId());
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countUserToadSize", toadIngameService.countAllUserToad(user.getId()));
        model.addAttribute("countUserCommonSize", toadIngameService.countUserToadByRarity(user.getId(), 0));
        model.addAttribute("countUserRareSize", toadIngameService.countUserToadByRarity(user.getId(), 1));
        model.addAttribute("countUserEpicSize", toadIngameService.countUserToadByRarity(user.getId(), 2));
        model.addAttribute("countUserMythicalSize", toadIngameService.countUserToadByRarity(user.getId(), 3));
        model.addAttribute("countUserLegendSize", toadIngameService.countUserToadByRarity(user.getId(), 4));
        if (listUserToadCommonRarity.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "myToadCategory";
        } else {
            model.addAttribute("myToadList", listUserToadCommonRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "myToadCategory";
    }

    @GetMapping("/searchUserToadByName")
    public String searchUserToadByName(Model model,
                                       @RequestParam(value = "searchUserToadByName", required = false) String name) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        model.addAttribute("countUserToadSize", toadIngameService.countAllUserToad(user.getId()));
        model.addAttribute("countUserCommonSize", toadIngameService.countUserToadByRarity(user.getId(), 0));
        model.addAttribute("countUserRareSize", toadIngameService.countUserToadByRarity(user.getId(), 1));
        model.addAttribute("countUserEpicSize", toadIngameService.countUserToadByRarity(user.getId(), 2));
        model.addAttribute("countUserMythicalSize", toadIngameService.countUserToadByRarity(user.getId(), 3));
        model.addAttribute("countUserLegendSize", toadIngameService.countUserToadByRarity(user.getId(), 4));
        if (name.trim() == "") {
            model.addAttribute("mess", "Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "myToadCategory";
        }
        List<ToadIngame> listToadUserSearchByName = toadIngameService.findUserToadByName(name, user.getId());
        model.addAttribute("searchName", name);
        if (listToadUserSearchByName.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess", "It's seem like you don't have Toad name that you're looking for, Please try another!");
            return "myToadCategory";
        } else {
            model.addAttribute("myToadList", listToadUserSearchByName);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "myToadCategory";
    }
}
