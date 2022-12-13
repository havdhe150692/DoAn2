package com.example.doan2.controller;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.ItemInventory;
import com.example.doan2.entity.User;
import com.example.doan2.service.ContractExecutionService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/marketplaceApi/")
public class MarketplaceNFTController {

    @Autowired
    ContractExecutionService contractExecutionService;

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
