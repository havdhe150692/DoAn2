package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import com.example.doan2.repository.MarketRepositoty;
import com.example.doan2.repository.ToadRepository;
import com.example.doan2.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.Date;

import java.sql.Timestamp;

@Controller
public class SellToadController {

    ToadRepository toadRepo;

    @Autowired
    private MarketRepositoty marketRepo;

    @GetMapping("/sellToad/{id}")
    public String sellToad(Model model, @PathVariable("id") int id) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        Toad myToad = toadRepo.findById(id);
        model.addAttribute("myToad", myToad);
        return "sellToad";
    }

    @PostMapping("/sellProcessing/{id}")
    public String sellProcess(@RequestParam(value = "price", required = false) int price,
                              @PathVariable("id") int id,
                              Market market,
                              Model model) {
//        if(price < 0 || price > 1000000) {
//            model.addAttribute("errorPrice", "Max price only 1000000$");
//            return "/sellToad/{id}";
//        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        market.setPrice(price);
        Date date = new Date();
        market.setTime(new Timestamp(date.getTime()));
        System.out.println("this is time: " + market.getTime());
        market.setSeller(user);
        Toad myToad = toadRepo.findById(id);
       // market.setToad(myToad);
        marketRepo.save(market);
        return "myToadCategory";
    }

}
