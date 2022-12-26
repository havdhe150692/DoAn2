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
import com.example.doan2.utils.Enum;
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

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Controller
public class SellToadController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    ToadIngameService toadIngameService;

    @Autowired
    MarketService marketService;

    @Autowired
    MarketRepositoty marketRepositoty;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @PostMapping("/cancelSellProcessing/{id}")
    public String cancelSellingToad(Model model, @PathVariable("id") int id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();
        try
        {
            // dem so coc cua minh
            // neu so coc cua minh co is_selling = 1 <10 con thi moi duoc huy ban
            var toad = toadIngameService.findById(id);
            var m =  marketRepositoty.findByToadIngame(toad);

            UserContractConnector userContractConnector = new UserContractConnector(user);
            userContractConnector.CancelSellNFT(m.getId());

            toad.setIsSelling(0);
            toadIngameRepository.save(toad);
            marketRepositoty.delete(m);
//            marketService.cancelSellToadAtMarket(id);
//            market.setIsSelling(0);
//            marketService.saveMarket(market);

            return "redirect:/sellToad/{id}";
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return "redirect:/sellToad/{id}";
        }


    }

    @GetMapping("/sellToad/{id}")

    public String sellToad(Model model, @PathVariable("id") int id) {
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
        if (auth == null || auth instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        try {
            UserContractConnector u = new UserContractConnector(user);
            BigInteger balance = u.GetBalance();
            model.addAttribute("balance", balance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        ToadIngame myToad = toadIngameService.findById(id);
        model.addAttribute("myToad", myToad);
        // Common rarity cannot be sold
        if (myToad.getToadData().getRarity().equals(Enum.Rarity.Common)) {
            model.addAttribute("sellCondition", Boolean.FALSE);
        } else {
            Market toadSellAtMarket = marketService.findToadBySellerAtMarket(id);
            // if toad is already placed at market
            if (toadSellAtMarket != null) {
                //cancel sell toad case
                model.addAttribute("sellCondition", Boolean.TRUE);
                model.addAttribute("cancelSellAtMarket", Boolean.FALSE);
            } else {
                //Display Sell button
                model.addAttribute("cancelSellAtMarket", Boolean.TRUE);
                model.addAttribute("sellCondition", Boolean.TRUE);
            }
        }
        return "sellToad";
    }

    @PostMapping("/sellProcessing/{id}")
    public String sellProcess(@RequestParam(value = "price", required = false) String price,
                              @PathVariable("id") int id,
                              Market market,
                              Model model) {
        if (price.equals("") || price == null) {
            model.addAttribute("errorPrice", "Price only allow 1 to 1000000$");
            return "redirect:/sellToad/{id}";
        }
        int sellPrice = Integer.parseInt(price);
        if (sellPrice <= 0 || sellPrice > 1000000) {
            model.addAttribute("errorPrice", "Price only allow 1 to 1000000$");
            return "redirect:/sellToad/{id}";
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();

        try
        {
            UserContractConnector userContractConnector = new UserContractConnector(user);
            var event = userContractConnector.ListNFT(id, sellPrice);
            System.out.println(event.itemId.intValue());

            market.setId(event.itemId.intValue());
            market.setPrice(sellPrice);
            Date date = new Date(System.currentTimeMillis() - (3600 * 1000) * 7);
            market.setTime(new Timestamp(date.getTime()));
            market.setSeller(user);
            market.setSelling(1);
            ToadIngame myToad = toadIngameService.findById(id);
            myToad.setIsSelling(1);
            market.setToadIngame(myToad);
            marketService.saveMarket(market);

            toadIngameRepository.save(myToad);


            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);

            System.out.println("asdadsdasdsasadsdadsaasddas");

            return "redirect:/myToad";

        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
            model.addAttribute("listToadClass", listToadClass);
            return "redirect:/myToad";
        }
    }
}
