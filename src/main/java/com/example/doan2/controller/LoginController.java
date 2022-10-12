package com.example.doan2.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
@RequestMapping(value = "/demo", method = GET)
public class LoginController {

    @GetMapping("")
    public String home(){
        return "webpages/login";
    }



}
