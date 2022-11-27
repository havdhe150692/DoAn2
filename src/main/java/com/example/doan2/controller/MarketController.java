package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class MarketController {

    @Autowired
    ToadIngameService toadIngameService;

    @Autowired
    MarketService marketService;

    @GetMapping("/shop")
    public String viewShop(Model model) {
        List<Market> listToad = marketService.findAll();
        if (listToad.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "myToadCategory";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listToad);
        }
        return "shop";
    }


    @GetMapping("/productDetail/{id}")
    public String viewProductDetail(Model model, @PathVariable("id") int id) {
        Market toadPrice = marketService.findById(id);
        ToadIngame myToad = toadIngameService.findById(id);
        model.addAttribute("myToad", myToad);
        model.addAttribute("myToadPrice", toadPrice);
        return "productDetail";
    }


}
