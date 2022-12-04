package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadData;
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
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if (listToad.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "shop";
        } else {
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
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(name.trim() == "") {
            model.addAttribute("mess","Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByName(name);
        model.addAttribute("searchName", name);
        if(listToadSearchByName.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess","It's seem like we don't have Toad name that you're looking for, Please try another!");
            return "shop";
        } else {
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
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(name.trim() == "") {
            model.addAttribute("mess","Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        model.addAttribute("searchNameContains", name);
        List<Market> listToadSearchByName = marketService.findByNameContain(name);
        if(listToadSearchByName.isEmpty()) {
            model.addAttribute("mess","It's seem like we don't have Toad contains the name that you're looking for, Please try another!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
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
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
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
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listToadFromCategories.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess","This type of Toad will be here soon , Keep waiting for us!");
            return "shop";
        } else {
            model.addAttribute("toadList", listToadFromCategories);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }
    @GetMapping("/findCommonRarity")
    public String viewCommonRarity(Model model) {
        List<Market> listCommonRarity = marketService.findByRarity(0);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listCommonRarity.isEmpty()) {
            model.addAttribute("mess","This toad Rarity will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("toadList", listCommonRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/findRareRarity")
    public String viewRareRarity(Model model) {
        List<Market> listRareRarity = marketService.findByRarity(1);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listRareRarity.isEmpty()) {
            model.addAttribute("mess","This toad Rarity will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("toadList", listRareRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/findEpicRarity")
    public String viewEpicRarity(Model model) {
        List<Market> listEpicRarity = marketService.findByRarity(2);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listEpicRarity.isEmpty()) {
            model.addAttribute("mess","This toad Rarity will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("toadList", listEpicRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/findMythicalRarity")
    public String viewMythicalRarity(Model model) {
        List<Market> listMythicalRarity = marketService.findByRarity(3);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listMythicalRarity.isEmpty()) {
            model.addAttribute("mess","This toad Rarity will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("toadList", listMythicalRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/findLegendRarity")
    public String viewLegendRarity(Model model) {
        List<Market> listLegendRarity = marketService.findByRarity(4);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listLegendRarity.isEmpty()) {
            model.addAttribute("mess","This toad Rarity will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("toadList", listLegendRarity);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }
    @GetMapping("/sortFromHighestPrice")
    public String sortFromHighestPrice(Model model) {
        List<Market> listSortByHighestPrice = marketService.sortFromHighestPrice();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listSortByHighestPrice.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "shop";
        } else {
            model.addAttribute("toadList", listSortByHighestPrice);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/sortFromLowestPrice")
    public String sortFromLowestPrice(Model model) {
        List<Market> listSortByLowestPrice = marketService.sortFromLowestPrice();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToad(0));
        model.addAttribute("countRareSize", marketService.countToad(1));
        model.addAttribute("countEpicSize", marketService.countToad(2));
        model.addAttribute("countMythicalSize", marketService.countToad(3));
        model.addAttribute("countLegendSize", marketService.countToad(4));
        if(listSortByLowestPrice.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "shop";
        } else {
            model.addAttribute("toadList", listSortByLowestPrice);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }
}
