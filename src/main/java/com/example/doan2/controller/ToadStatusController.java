package com.example.doan2.controller;

import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.UserRepository;
import com.example.doan2.service.ToadStatusLogicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/checkStatus/")
public class ToadStatusController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    ToadStatusLogicService toadStatusLogicService;

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/collect/{userId}", method= RequestMethod.GET)
    public void getStatusCollect()
    {

    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/feed",  method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String requestFeed(int userId, int toadId) throws Exception {
        User u = userRepository.findById(userId);
        ToadIngame t = toadIngameRepository.findById(toadId);
        UserContractConnector i = new UserContractConnector(u);
        if(u != null && t != null )
        {
            Instant inst = Instant.now();
            if(inst.compareTo(t.getToadStatus().getExpectedHungry().toInstant())
                    >= 0)
            {
                toadStatusLogicService.ToadFeed(t);
                return t.getToadStatus().getExpectedHungry().toString();
            }

        }
        return t.getToadStatus().getExpectedHungry().toString();

    }
}
