package com.example.doan2.controller;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.MarketService;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class MarketController {

    @Autowired
    ToadIngameService toadIngameService;

    @Autowired
    MarketService marketService;

    @Autowired
    UserService userService;

    @GetMapping("/market")
    public String viewMarket(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        } else {
            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);
            model.addAttribute("countEconomicToad", marketService.countByToadClass(2));
            model.addAttribute("countGraphicToad", marketService.countByToadClass(4));
            model.addAttribute("countArtistToad", marketService.countByToadClass(6));
            model.addAttribute("countLectureToad", marketService.countByToadClass(8));
            model.addAttribute("countAIToad", marketService.countByToadClass(5));
            model.addAttribute("countSoftwareToad", marketService.countByToadClass(3));
            return "market";
        }
    }

    @GetMapping("/shop")
    public String viewShop(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        List<Market> listToad = marketService.findAll();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
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
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        Market toadDetail = marketService.findById(id);
        model.addAttribute("toadDetail", toadDetail);
        Market seller = marketService.findSellerToad(id);
        model.addAttribute("seller", seller);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        if (seller.getSeller().getName().equals(user.getName())) {
            model.addAttribute("viewMarketBySeller", Boolean.FALSE);
        } else {
            model.addAttribute("viewMarketBySeller", Boolean.TRUE);
        }
        return "productDetail";
    }

    @PostMapping("/searchToadByName")
    public String searchToadByName(Model model,
                                   @RequestParam(value = "searchByName", required = false) String name) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (name.trim() == "") {
            model.addAttribute("mess", "Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByName(name);
        model.addAttribute("searchName", name);
        if (listToadSearchByName.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess", "It's seem like we don't have Toad name that you're looking for, Please try another!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (name.trim() == "") {
            model.addAttribute("mess", "Please enter searchValue");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        model.addAttribute("searchNameContains", name);
        List<Market> listToadSearchByName = marketService.findByNameContain(name);
        if (listToadSearchByName.isEmpty()) {
            model.addAttribute("mess", "It's seem like we don't have Toad contains the name that you're looking for, Please try another!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (priceTo == null || priceFrom == null) {
            model.addAttribute("errorPrice", "Not allow Null values!");
            return "shop";
        }
        int from = Integer.parseInt(priceFrom);
        int to = Integer.parseInt(priceTo);
        if (from > to) {
            model.addAttribute("errorPrice", "Price in range is invalid!");
            return "shop";
        } else {
            List<Market> listToadSearchBetweenPrice = marketService.findBetweenPrice(from, to);
            if (listToadSearchBetweenPrice.isEmpty()) {
                model.addAttribute("errorPrice", "We don't have toad price in that range , please try another!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listToadFromCategories.isEmpty()) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listCommonRarity.isEmpty()) {
            model.addAttribute("mess", "This toad Rarity will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listRareRarity.isEmpty()) {
            model.addAttribute("mess", "This toad Rarity will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listEpicRarity.isEmpty()) {
            model.addAttribute("mess", "This toad Rarity will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listMythicalRarity.isEmpty()) {
            model.addAttribute("mess", "This toad Rarity will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listLegendRarity.isEmpty()) {
            model.addAttribute("mess", "This toad Rarity will be here soon , Keep waiting for us!");
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listSortByHighestPrice.isEmpty()) {
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
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listSortByLowestPrice.isEmpty()) {
            model.addAttribute("condition", Boolean.FALSE);
            return "shop";
        } else {
            model.addAttribute("toadList", listSortByLowestPrice);
            model.addAttribute("condition", Boolean.TRUE);
        }
        return "shop";
    }

    @GetMapping("/EconomicToad")
    public String viewEconomicToad(Model model) {
        List<Market> listEconomicToad = marketService.findEconomicToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listEconomicToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listEconomicToad);
        }
        return "shop";
    }

    @GetMapping("/GraphicToad")
    public String viewGraphicToad(Model model) {
        List<Market> listGraphicToad = marketService.findGraphicToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listGraphicToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listGraphicToad);
        }
        return "shop";
    }

    @GetMapping("/ArtistToad")
    public String viewArtistToad(Model model) {
        List<Market> listArtistToad = marketService.findArtistToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listArtistToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listArtistToad);
        }
        return "shop";
    }

    @GetMapping("/LectureToad")
    public String viewLectureToad(Model model) {
        List<Market> listLectureToad = marketService.findLectureToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listLectureToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listLectureToad);
        }
        return "shop";
    }

    @GetMapping("/AIToad")
    public String viewAIToad(Model model) {
        List<Market> listAIToad = marketService.findAIToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listAIToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listAIToad);
        }
        return "shop";
    }

    @GetMapping("/SoftwareToad")
    public String viewSoftwareToad(Model model) {
        List<Market> listSoftwareToad = marketService.findSoftwareToad();
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (listSoftwareToad.isEmpty()) {
            model.addAttribute("mess", "This type of Toad will be here soon , Keep waiting for us!");
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", listSoftwareToad);
        }
        return "shop";
    }

    @PostMapping("/buyToad/{id}")
    public String buyToad(Model model, @PathVariable("id") int id) {
        marketService.removeToadAtMarket(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        toadIngameService.changeToadOwner(user.getId(), id);
        return "redirect:/shop";
    }

    @GetMapping("/paging")
    public String pagingMarket(Model model,
                               @RequestParam(value = "page", required = false) Optional<Integer> page
//                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
                               ) {

        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(1);
        System.out.println("currentPage: " + currentPage);
//        System.out.println("pageSize: " + pageSize);
        Page<Market> toadPagingMarket = marketService.pagingMarket(PageRequest.of(currentPage - 1, 5));
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute("toadList", toadPagingMarket);
        System.out.println("this is toadPaging market: " + toadPagingMarket.getTotalElements());
        int totalPages = toadPagingMarket.getTotalPages();
        System.out.println("this is total pages: " + totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers",pageNumbers);
            model.addAttribute("currentPage", currentPage);
        }
        return "shop";
    }
}
