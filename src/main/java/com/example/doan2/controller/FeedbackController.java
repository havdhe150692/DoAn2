package com.example.doan2.controller;

import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.Feedback;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.User;
import com.example.doan2.service.FeedbackService;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.service.MarketService;
import com.example.doan2.service.ToadIngameService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class FeedbackController {

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    ToadIngameService toadIngameService;

    @GetMapping("/feedbackGame")
    public String feedbackGame(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "loginMarket";
        }
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        User user = ((UserServiceImp) authentication.getPrincipal()).getUser();
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        try {
            UserContractConnector u = new UserContractConnector(user);
            BigInteger balance = u.GetBalance();
            model.addAttribute("balance", balance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        if (feedbackService.userFeedback(user.getId()) != null) {
            model.addAttribute("updateFeedback", Boolean.TRUE);
            model.addAttribute("userUpdateFeedback",feedbackService.userFeedback(user.getId()));
        } else {
            model.addAttribute("updateFeedback", Boolean.FALSE);
        }
        List<Feedback> listFeedback = feedbackService.findAll();
        if (listFeedback.isEmpty()) {
            model.addAttribute("feedbackEmpty", Boolean.TRUE);
        } else {
            model.addAttribute("feedbackEmpty", Boolean.FALSE);
            model.addAttribute("listFeedback", listFeedback);
        }
        return "feedbackGame";
    }


    @PostMapping("/feedbackProcessing")
    public String feedbackProcessing(Model model,
                                     @RequestParam(value = "feedback", required = false) String feedback,
                                     @RequestParam(value = "rating", required = false) int ratingNum) {
        List<ToadClass> listToadClass = toadIngameService.findAllToadClass();
        model.addAttribute("listToadClass", listToadClass);
        Feedback feedb = new Feedback();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserServiceImp) auth.getPrincipal()).getUser();

        System.out.println("this is rating num: " + ratingNum);
        System.out.println("this is rating feedback: " + feedback);
        Feedback userFeedback = feedbackService.userFeedback(user.getId());
        try {
            UserContractConnector u = new UserContractConnector(user);
            BigInteger balance = u.GetBalance();
            model.addAttribute("balance", balance);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        if (userFeedback != null) {
            if (feedback == null || feedback.trim().equals("")) {
                model.addAttribute("feedbackMess", "not allow null value");
                return "feedbackGame";
            } else if (feedback.trim().length() <= 5) {
                model.addAttribute("feedbackMess", "Write at least 5 characters!");
                return "feedbackGame";
            } else {
                //Update here
                System.out.println("this is rating num2: " + ratingNum);
                System.out.println("this is rating feedback2: " + feedback);
                Date date = new Date(System.currentTimeMillis() - (3600 * 1000) * 7);
//                feedbackService.updateUserFeedback(ratingNum, feedback, user.getId(), new Timestamp(date.getTime()));
                userFeedback.setInfo(feedback);
                userFeedback.setRatePoint(ratingNum);
                userFeedback.setTimePost(new Timestamp(date.getTime()));
                feedbackService.saveFeedback(userFeedback);
            }
        } else {
            if (feedback == null || feedback.trim().equals("")) {
                model.addAttribute("feedbackMess", "not allow null value");
                return "feedbackGame";
            } else if (feedback.trim().length() <= 5) {
                model.addAttribute("feedbackMess", "Write at least 5 characters!");
                return "feedbackGame";
            } else {
                //add new feedback here
                System.out.println("this is rating num3: " + ratingNum);
                System.out.println("this is rating feedback3: " + feedback);
                feedb.setInfo(feedback);
                Date date = new Date(System.currentTimeMillis() - (3600 * 1000) *7);
                feedb.setTimePost(new Timestamp(date.getTime()));
                feedb.setUserId(user);
                feedb.setRatePoint(ratingNum);
                feedbackService.saveFeedback(feedb);
            }
        }
        return "redirect:/feedbackGame";
    }
}
