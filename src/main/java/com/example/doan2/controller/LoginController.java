package com.example.doan2.controller;


import com.example.doan2.entity.User;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.NonceGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    UserService userService;

    NonceGenerateUtils nonceGenerateUtils;

    @RequestMapping(value="/employees?publicAddress=${publicAddress}", method= RequestMethod.GET)
    public String readUserAndReturnNonce(@PathVariable(value = "publicAddress") String publicAddress)  {
        User u = userService.findUserByPublicAddress(publicAddress);
        if(u == null)
        {
            return null;
        }
        else
        {
            return nonceGenerateUtils.newRandom12BytesNonce();
        }
    }

}
