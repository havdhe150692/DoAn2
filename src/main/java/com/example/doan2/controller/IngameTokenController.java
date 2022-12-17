package com.example.doan2.controller;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.AdminContractExecutionService;
import com.example.doan2.service.ToadStatusLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.time.Instant;

@RestController
@RequestMapping("/tokenApi/")
public class IngameTokenController {

    @Autowired
     UserRepository userRepository;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    ToadStatusLogicService toadStatusLogicService;


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
    @RequestMapping(value ="/collectMoney",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public BigInteger requestCollectMoney(int userId, int toadId) throws Exception {
        User u = userRepository.findById(userId);
        ToadIngame t = toadIngameRepository.findById(toadId);
        UserContractConnector i = new UserContractConnector(u);
        if(u != null && t != null )
        {
            Instant inst = Instant.now();
            if(inst.compareTo(t.getToadStatus().getExpectedCollect().toInstant())
                >= 0)
            {
                toadStatusLogicService.ToadCollect(t);
                int money = 0;
                System.out.println(t.getToadData().getRarity());
                switch (t.getToadData().getRarity())
                {

                    case Common -> {
                        money = 50;
                    }
                    case Rare -> {
                        money = 200;
                    }
                    case Epic -> {
                        money = 600;
                    }
                    case Mythical -> {
                        money = 1000;
                    }
                    case Legendary -> {
                        money = 2000;
                    }
                }
                System.out.print(money);
                i.RequestMoney(money);
            }
             return i.GetBalance();
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

            AdminContractExecutionService c = new AdminContractExecutionService();
            c.TransferFromImplementationExecution(u1, u2, 500);

            BigInteger balance =  i.GetBalance();
            System.out.println("Balance of user " + u1.getName() + " is " + balance);

            return balance;

        }
        return BigInteger.valueOf(0);
    }
}
