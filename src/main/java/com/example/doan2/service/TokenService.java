package com.example.doan2.service;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.User;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class TokenService {


    public BigInteger CheckBalance(User u) throws Exception {
        UserContractConnector i = new UserContractConnector(u);
        BigInteger balance =  i.GetBalance();
        System.out.println("Balance of user " + u.getName() + " is " + balance);
        return balance;
    }
}
