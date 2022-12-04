package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.MarketService;
import com.example.doan2.service.ToadIngameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
import java.util.List;

@Controller
public class SellToadController {

    @Autowired
    ToadIngameService toadIngameService;

    @Autowired
    MarketService marketService;

    @GetMapping("/sellToad/{id}")
    public String sellToad(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        ToadIngame myToad = toadIngameService.findById(id);
        model.addAttribute("myToad", myToad);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "sellToad";
    }

    @PostMapping("/sellProcessing/{id}")
    public String sellProcess(@RequestParam(value = "price", required = false) String price,
                              @PathVariable("id") int id,
                              Market market,
                              Model model) {
        if(price.equals("") || price == null) {
            model.addAttribute("errorPrice", "Price only allow 1 to 1000000$");
            return "redirect:/sellToad/{id}";
        }
        int sellPrice = Integer.parseInt(price);
        if(sellPrice <= 0 || sellPrice > 1000000) {
            model.addAttribute("errorPrice", "Price only allow 1 to 1000000$");
            return "redirect:/sellToad/{id}";
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        market.setPrice(sellPrice);
        Date date = new Date(System.currentTimeMillis() - (3600 * 1000)*7);
        market.setTime(new Timestamp(date.getTime()));
        market.setSeller(user);

        ToadIngame myToad = toadIngameService.findById(id);
        market.setToadIngame(myToad);
        marketService.saveMarket(market);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "redirect:/myToad";
    }

}
