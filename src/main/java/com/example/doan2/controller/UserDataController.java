package com.example.doan2.controller;


import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.TokenService;
import com.example.doan2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.List;

@RestController
@RequestMapping("/userData/")
public class UserDataController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    TokenService tokenService;

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/goodbye/{userId}", method= RequestMethod.GET)
    public String cleanUser(@PathVariable(value = "userId") int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
           UserContractConnector userContractConnector = new UserContractConnector(u);
           userContractConnector.ReturnAllMoney();
            var l = toadIngameRepository.findAllByOwner(u);
            for (int i = 0; i < l.size(); i++) {
                ToadIngame toadIngame =  l.get(i);
                toadIngameRepository.delete(toadIngame);
            }
        }
        return null;
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{userId}", method= RequestMethod.GET)
    public String getBalance(@PathVariable(value = "userId") int userId) throws Exception {
        User u = userRepository.findById(userId);
        if(u != null)
        {
            JSONObject jsonObject = new JSONObject();
            List<ToadIngame> t = toadIngameRepository.findAllByOwner(u);
            var balance = tokenService.CheckBalance(u);

            boolean isNewPlayer = true;
            if((balance.compareTo(BigInteger.ZERO) > 0) || t.size() > 0)
            {
                isNewPlayer =false;
            }
            jsonObject.put("id", u.getId());
            jsonObject.put("name",u.getName());
            jsonObject.put("toadCount", t.size());
            jsonObject.put("balance", balance);
            jsonObject.put("isNewPlayer", isNewPlayer);

            return jsonObject.toString();
        }
        return null;
    }
}
