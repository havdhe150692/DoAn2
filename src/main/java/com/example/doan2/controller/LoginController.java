package com.example.doan2.controller;


import com.example.doan2.entity.User;
import com.example.doan2.service.Impl.UserServiceImp;
import com.example.doan2.utils.NonceGenerateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/")
public class LoginController {

    @Autowired
    UserServiceImp userServiceImp;

    NonceGenerateUtils nonceGenerateUtils = new NonceGenerateUtils();

//    @CrossOrigin(origins ="http://localhost")
//    @RequestMapping(value ="/authentication/{publicAddress}", method= RequestMethod.GET)
//    public String readUserAndReturnNonce(@PathVariable(value = "publicAddress") String publicAddress)  {
//        User u = userService.findUserByPublicAddress(publicAddress);
//        if(u != null)
//        {
//            return u.getNonce();
//        }
//        else
//        {
//            return "Bruh";
//        }
//    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/testLogin/{username}", method= RequestMethod.GET)
    public boolean testLogin(@PathVariable(value = "username") String username)  {
        User u = userServiceImp.findUserByName(username);
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
        User u = userServiceImp.findUserByName(username);
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
        User u = userServiceImp.findUserById(id);
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
