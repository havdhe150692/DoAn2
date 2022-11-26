package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.MarketRepositoty;
import com.example.doan2.repository.ToadIngameRepository;
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

import java.sql.Timestamp;
import java.util.Date;

@Controller
public class SellToadController {
    @Autowired
    ToadRepository toadRepo;

    @Autowired
    ToadIngameRepository toadIngameRepo;

    @Autowired
    private MarketRepositoty marketRepo;

    @GetMapping("/sellToad/{id}")
    public String sellToad(Model model, @PathVariable("id") int id) {
//        Toad myToad1 = toadRepo.findById(id);
        ToadIngame myToad = toadIngameRepo.findById(id);
        model.addAttribute("myToad", myToad);
        return "sellToad";
    }

    @PostMapping("/sellProcessing/{id}")
    public String sellProcess(@RequestParam(value = "price", required = false) int price,
                              @PathVariable("id") int id,
                              Market market,
                              Model model) {
        if(price <= 0 || price > 1000000) {
            model.addAttribute("errorPrice", "Price only allow 1 to 1000000$");
            return "redirect:/sellToad/{id}";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        market.setPrice(price);
        Date date = new Date(System.currentTimeMillis() - (3600 * 1000)*7);
        market.setTime(new Timestamp(date.getTime()));
        market.setSeller(user);
        ToadIngame myToad = toadIngameRepo.findById(id);
        market.setToadIngame(myToad);
        marketRepo.save(market);
        return "redirect:/myToad";
    }

}
