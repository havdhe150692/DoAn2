package com.example.doan2.controller;

import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadRepository;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.UserLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class MyToadCategoryController {

    @Autowired
    ToadRepository toadRepo;

    @Autowired
    UserRepository userRepo;

    @GetMapping("/myToad")
    public String showMyToad(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = ((UserLoginService) auth.getPrincipal()).getUser();
        List<Toad> myToadLists = toadRepo.findAllByOwner(user);
        model.addAttribute("myToadList", myToadLists);
        return "myToadCategory";
    }

//    @GetMapping("/sellToad")
//    public String sellToad(Model model) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        User user = ((UserLoginService) auth.getPrincipal()).getUser();
//        int userId = user.getId();
//        Toad myToad = toadRepo.findById(userId);
//        System.out.println("this is my toad" + myToad);
//        System.out.println("this is my toad" + myToad);
//        System.out.println("this is my toad" + myToad);
//        model.addAttribute("myToad", myToad);
//        return "sellToad";
//    }

//    @RequestMapping("/menu")
//    public class ImageController {
//
//
//
//        @RequestMapping(value = "/imageDisplay", method = RequestMethod.GET)
//        public void showImage(@RequestParam("id") Integer itemId, HttpServletResponse response, HttpServletRequest request) throws ServletException, IOException {
//
//
//            Item item = itemService.get(itemId);
//            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
//            response.getOutputStream().write(item.getItemImage());
//
//
//            response.getOutputStream().close();
//        }
//
//    }
}
