package com.example.doan2.controller;


import com.example.doan2.chain.InGameContractConnector;
import com.example.doan2.entity.ItemInventory;
import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/tokenApi/")
public class IngameTokenController {

    @Autowired
    private UserRepository userRepository;


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/balance/{userId}", method= RequestMethod.GET)
    public BigInteger getBalance(@PathVariable(value = "userId") int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
            InGameContractConnector i = new InGameContractConnector(u);
            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u.getName() + " is " + balance);
            return balance;

        }
        return BigInteger.valueOf(0);
    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/requestMoney/{userId}", method= RequestMethod.GET)
    public BigInteger requestMoney(@PathVariable(value = "userId") int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
            InGameContractConnector i = new InGameContractConnector(u);
            i.RequestMoney();
            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u.getName() + " is " + balance);
            return balance;

        }
        return BigInteger.valueOf(0);
    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/requestMoney",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public void requestMoneyPost(int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
            InGameContractConnector i = new InGameContractConnector(u);
            i.RequestMoney();
            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u.getName() + " is " + balance);

        }

    }
}
