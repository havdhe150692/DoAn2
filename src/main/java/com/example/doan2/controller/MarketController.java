package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
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
            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);
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
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "productDetail";
    }

    //    @PostMapping("/filterPrice")
    @RequestMapping(value = "/filterPrice", method = RequestMethod.POST)
    public String viewToadPriceFilter(Model model, @RequestParam(value = "price", required = false) int[] filterPrice) {
        System.out.println("this is filter price: " + filterPrice);
        if(filterPrice != null) {
            List<Market> listFilterByPrice1 = new ArrayList<>();
            List<Market> listFilterByPrice2 = new ArrayList<>();
            List<Market> listFilterByPrice3 = new ArrayList<>();
            List<Market> listFilterByPrice4 = new ArrayList<>();
            List<Market> listFilterByPrice5 = new ArrayList<>();
            for (int i = 0; i < filterPrice.length; i++) {
//                System.out.println("this is list Filter at position: " + filterPrice[i]);
                if(filterPrice[i] == 1) {
//                    System.out.println("this is list Filter at position: " + filterPrice[i]);
                    listFilterByPrice1 = marketService.findBetweenPrice(0, 100);
                    System.out.println("this is list 1: " + listFilterByPrice1);

                }
                if(filterPrice[i] == 2) {
                     listFilterByPrice2 = marketService.findBetweenPrice(100, 200);
                    System.out.println("this is list 2: " + listFilterByPrice2);
                }
                if(filterPrice[i] == 3) {
                     listFilterByPrice3 = marketService.findBetweenPrice(200, 300);
                    System.out.println("this is list 3: " + listFilterByPrice3);
                }
                if(filterPrice[i] == 4) {
                    listFilterByPrice4 = marketService.findBetweenPrice(300, 400);
                    System.out.println("this is list 4: " + listFilterByPrice4);
                }
                if(filterPrice[i] == 5) {
                     listFilterByPrice5 = marketService.findBetweenPrice(400, 500);
                    System.out.println("this is list 5: " + listFilterByPrice5);
                }
            }
            LinkedList<Market> merged = new LinkedList<>();
            merged.addAll(listFilterByPrice1);
            merged.addAll(listFilterByPrice2);
            merged.addAll(listFilterByPrice3);
            merged.addAll(listFilterByPrice4);
            merged.addAll(listFilterByPrice5);
            System.out.println("list merged" + merged);
            model.addAttribute("finalListFilter" + merged);
        }
        return "shop";

    }


}
