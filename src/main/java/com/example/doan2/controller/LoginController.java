package com.example.doan2.controller;


import com.example.doan2.entity.User;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.NonceGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@RestController
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    UserService userService;

    NonceGenerateUtils nonceGenerateUtils = new NonceGenerateUtils();

    @CrossOrigin(origins ="http://localhost")
    @RequestMapping(value ="/authentication/{publicAddress}", method= RequestMethod.GET)
    public String readUserAndReturnNonce(@PathVariable(value = "publicAddress") String publicAddress)  {
        User u = userService.findUserByPublicAddress(publicAddress);
        if(u != null)
        {
            return u.getNonce();
        }
        else
        {
            return "Bruh";
        }
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/testLogin/{username}", method= RequestMethod.GET)
    public boolean testLogin(@PathVariable(value = "username") String username)  {
        User u = userService.findUserByName(username);
        if(u != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/testLogin1/username={username}_password={password}", method= RequestMethod.GET)
    public boolean testLoginForm(@PathVariable(value = "username") String username)  {
        User u = userService.findUserByName(username);
        if(u != null)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/Id/{id}", method= RequestMethod.GET)
    public String getId(@PathVariable(value = "id") int id)  {
        User u = userService.findUserById(id);
        if(u != null)
        {
            return u.getName();
        }
        else
        {
            return "null";
        }
    }



}
