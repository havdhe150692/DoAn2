package com.example.doan2.controller;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.User;
import com.example.doan2.service.AdminContractExecutionService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/marketplaceApi/")
public class MarketplaceNFTController {

    @Autowired
    AdminContractExecutionService adminContractExecutionService;

    @Autowired
    UserService userService;

    @RequestMapping(value ="/listNFT", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String listNFT(int userId, int tokenId, int price) throws Exception {
        User u = userService.findUserById(userId);
        if(u != null)
        {
            UserContractConnector userContractConnector = new UserContractConnector(u);
            userContractConnector.ListNFT(tokenId, price);
        }

        return "";
    }
}
