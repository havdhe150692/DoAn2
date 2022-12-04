package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.service.MarketService;
import com.example.doan2.service.ToadIngameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class MarketController {

    @Autowired
    ToadIngameService toadIngameService;

    @Autowired
    MarketService marketService;

    @GetMapping("/shop")
    public String viewShop(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        List<Market> listToad = marketService.findAll();
        if (listToad.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "shop";
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        Market toadPrice = marketService.findById(id);
        ToadIngame toadDetail = toadIngameService.findById(id);
        model.addAttribute("toadDetail", toadDetail);
        model.addAttribute("toadPrice", toadPrice);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        return "productDetail";
    }

    @PostMapping("/searchToadByName")
    public String searchToadByName(Model model,
                                   @RequestParam(value = "searchByName", required = false) String name) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        if(name.trim() == "") {
            model.addAttribute("mess","Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByName(name);
        if(listToadSearchByName.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess","It's seem like we don't have Toad name that you're looking for, Please try another!");
            return "shop";
        } else {
            model.addAttribute("searchName", name);
            model.addAttribute("toadList", listToadSearchByName);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }
    @PostMapping("/searchToadContainsName")
    public String searchToadContainsString(Model model,
                                           @RequestParam(value = "searchByNameContains", required = false) String name) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        if(name.trim() == "") {
            model.addAttribute("mess","Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByNameContain(name);
        if(listToadSearchByName.isEmpty()) {
            model.addAttribute("mess","It's seem like we don't have Toad contains the name that you're looking for, Please try another!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("searchNameContains", name);
            model.addAttribute("toadList", listToadSearchByName);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }


    @PostMapping("/searchByPriceBetween")
    public String searchToadByPrice(Model model,
                                    @RequestParam(value = "priceFrom", required = false) String priceFrom,
                                    @RequestParam(value = "priceTo", required = false) String priceTo) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        if(priceTo == null || priceFrom == null) {
            model.addAttribute("errorPrice","Not allow Null values!");
            return "shop";
        }
        int from = Integer.parseInt(priceFrom);
        int to = Integer.parseInt(priceTo);
        if(from > to) {
            model.addAttribute("errorPrice","Price in range is invalid!");
            return "shop";
        } else {
            List<Market> listToadSearchBetweenPrice = marketService.findBetweenPrice(from,to);
            if(listToadSearchBetweenPrice.isEmpty()) {
                model.addAttribute("errorPrice","We don't have toad price in that range , please try another!");
                model.addAttribute("condition", Boolean.TRUE);
                return "shop";
            } else {
                model.addAttribute("from", from);
                model.addAttribute("to", to);
                model.addAttribute("toadList", listToadSearchBetweenPrice);
                model.addAttribute("condition", Boolean.TRUE);
            }
        }
        return "shop";
    }

    @GetMapping("categories/{id}")
    public String viewToadByCategories(@PathVariable("id") int id, Model model) {
        List<Market> listToadFromCategories = marketService.findByToadClass(id);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        if(listToadFromCategories.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess","This type of Toad will be release soon , Keep waiting for us!");
            return "shop";
        } else {
            model.addAttribute("toadList", listToadFromCategories);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }


}
