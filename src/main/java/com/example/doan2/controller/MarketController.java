package com.example.doan2.controller;

import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.MarketRepositoty;

import com.example.doan2.entity.*;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.service.FeedbackService;

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


    @Autowired
    MarketRepositoty marketRepositoty;

    @Autowired
    FeedbackService feedbackService;


    @Autowired
    ToadIngameRepository toadIngameRepository;


    @GetMapping("/market")
    public String viewMarket(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        User user = ((UserServiceImp) authentication.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {

            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
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

    @GetMapping("/shop")
    public String viewShop(Model model,
                           @RequestParam(value = "page", required = false) Optional<Integer> page,
                           @RequestParam(value = "size", required = false) Optional<Integer> size,
                           Market market) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {

            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        Page<Market> toadPagingMarket = marketService.pagingMarket(PageRequest.of(currentPage - 1, pageSize));
        System.out.println("this is toadPagingMarket: " + toadPagingMarket);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (toadPagingMarket == null) {
            model.addAttribute("condition", Boolean.FALSE);
            model.addAttribute("pagingEmpty", Boolean.TRUE);
            return "shop";
        } else {
            model.addAttribute("pagingEmpty", Boolean.FALSE);
            model.addAttribute("condition", Boolean.TRUE);
            int totalPages = toadPagingMarket.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("currentPage", currentPage);
            model.addAttribute("toadList", toadPagingMarket);

        }
        return "shop";
    }


    @GetMapping("/productDetail/{id}")
    public String viewProductDetail(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        Market toadDetail = marketService.findById(id);
        model.addAttribute("toadDetail", toadDetail);
        Market seller = marketService.findSellerToad(id);
        model.addAttribute("seller", seller);
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);

        if (seller.getSeller().getName().equals(user.getName())) {
            model.addAttribute("viewMarketBySeller", Boolean.FALSE);
        } else {
            model.addAttribute("viewMarketBySeller", Boolean.TRUE);
        }
        return "productDetail";
    }

    @PostMapping("/searchToadByName")
    public String searchToadByName(Model model,
                                   @RequestParam(value = "searchByName", required = false) String name,
                                   @RequestParam(value = "page", required = false) Optional<Integer> page,
                                   @RequestParam(value = "size", required = false) Optional<Integer> size) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        if (name.trim().equals("")) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("pagingEmpty", Boolean.TRUE);
            model.addAttribute("mess", "Please enter searchValue");
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByName(name);
        model.addAttribute("searchName", name);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        Page<Market> toadPagingMarket = marketService.customPaging(
                PageRequest.of(currentPage - 1, pageSize), listToadSearchByName);
        if (toadPagingMarket == null) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("pagingEmpty", Boolean.TRUE);
            model.addAttribute("mess", "It's seem like we don't have Toad name that you're looking for, Please try another!");
            return "shop";
        } else {
            model.addAttribute("pagingEmpty", Boolean.FALSE);
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", toadPagingMarket);
            int totalPages = toadPagingMarket.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            model.addAttribute("currentPage", currentPage);
        }
        return "shop";
    }

    @PostMapping("/searchToadContainsName")
    public String searchToadContainsString(Model model,
                                           @RequestParam(value = "searchByNameContains", required = false) String name,
                                           @RequestParam(value = "page", required = false) Optional<Integer> page,
                                           @RequestParam(value = "size", required = false) Optional<Integer> size) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        if (name.trim().equals("")) {
            model.addAttribute("mess", "Please enter searchValue");
            model.addAttribute("pagingEmpty", Boolean.TRUE);
            model.addAttribute("condition", Boolean.TRUE);
            return "shop";
        }
        List<Market> listToadSearchByName = marketService.findByNameContain(name);
        model.addAttribute("searchNameContains", name);
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(6);
        Page<Market> toadPagingMarket = marketService.customPaging(
                PageRequest.of(currentPage - 1, pageSize), listToadSearchByName);
        if (toadPagingMarket == null) {
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("pagingEmpty", Boolean.TRUE);
            model.addAttribute("mess", "It's seem like we don't have Toad contains the name " +
                    "that you're looking for, Please try another!");
            return "shop";
        } else {
            model.addAttribute("pagingEmpty", Boolean.FALSE);
            model.addAttribute("condition", Boolean.TRUE);
            model.addAttribute("toadList", toadPagingMarket);
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
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

    @GetMapping("/findByRarity/{rarity}")
    public String viewCommonRarity(@PathVariable("rarity") int rarity, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        List<Market> listCommonRarity = marketService.findByRarity(rarity);
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


    @GetMapping("/sortFromHighestPrice")
    public String sortFromHighestPrice(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
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
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
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

    @PostMapping("/buyToad/{id}")
    public String buyToad(Model model, @PathVariable("id") int id, Market market) {
//        marketService.removeToadAtMarket(id);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        //   marketService.removeToadAtMarket(id);
        try
        {
            Market m = marketRepositoty.findById(id);
            UserContractConnector userContractConnector = new UserContractConnector(user);
            userContractConnector.BuyNFT(id,m.getPrice());
            marketRepositoty.delete(m);
            toadIngameService.changeToadOwner(user.getId(), m.getToadIngame().getId());

            ToadIngame myToad = toadIngameService.findById( m.getToadIngame().getId());
            myToad.setIsSelling(0);
            toadIngameRepository.save(myToad);


            return "redirect:/shop";
        }

        catch (Exception e)
        {
            System.out.println(e.toString());
            return "redirect:/shop";
        }


    }

    @GetMapping("/paging")
    public String pagingMarket(Model model,
                               @RequestParam(value = "page", required = false) Optional<Integer> page
//                               @RequestParam(value = "size", required = false) Optional<Integer> size) {
    ) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        if (userFeedback != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback", userFeedback);
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        int currentPage = page.orElse(1);
//        int pageSize = size.orElse(1);
        System.out.println("currentPage: " + currentPage);

//        System.out.println("pageSize: " + pageSize);

        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        model.addAttribute("countToadSize", marketService.countAllMarket());
        model.addAttribute("countCommonSize", marketService.countToadByRarity(0));
        model.addAttribute("countRareSize", marketService.countToadByRarity(1));
        model.addAttribute("countEpicSize", marketService.countToadByRarity(2));
        model.addAttribute("countMythicalSize", marketService.countToadByRarity(3));
        model.addAttribute("countLegendSize", marketService.countToadByRarity(4));
        model.addAttribute("pagingEmpty", Boolean.FALSE);
        model.addAttribute("condition", Boolean.TRUE);
        Page<Market> toadPagingMarket = marketService.pagingMarket(PageRequest.of(currentPage - 1, 6));
        model.addAttribute("condition", Boolean.TRUE);
        model.addAttribute("toadList", toadPagingMarket);
        System.out.println("this is toadPaging market: " + toadPagingMarket.getTotalElements());
        int totalPages = toadPagingMarket.getTotalPages();
        System.out.println("this is total pages: " + totalPages);
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("currentPage", currentPage);
        }
        return "shop";
    }
}
