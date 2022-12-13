package com.example.doan2.controller;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.User;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.ContractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;

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
            UserContractConnector i = new UserContractConnector(u);
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
            UserContractConnector i = new UserContractConnector(u);
            i.RequestMoney();
            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u.getName() + " is " + balance);
            return balance;

        }
        return BigInteger.valueOf(0);
    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/requestMoney",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BigInteger requestNewPlayerMoney(int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
            UserContractConnector i = new UserContractConnector(u);
            i.RequestMoney();
            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u.getName() + " is " + balance);
            return balance;
        }
        return BigInteger.valueOf(0);

    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/transferFrom/{userId1}/{userId2}", method= RequestMethod.GET)
    public BigInteger demoTransferFrom(@PathVariable(value = "userId1") int userId1, @PathVariable(value = "userId2") int userId2) throws Exception {
        User u1 = userRepository.findById(userId1);
        User u2 = userRepository.findById(userId2);
        if(u1 != null && u2 != null)
        {
            UserContractConnector i = new UserContractConnector(u1);

            ContractExecutionService c = new ContractExecutionService();
            c.TransferFromImplementationExecution(u1, u2, 500);

            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u1.getName() + " is " + balance);

            return balance;

        }
        return BigInteger.valueOf(0);
    }
}
