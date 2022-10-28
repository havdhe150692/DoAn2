package com.example.doan2.controller;


import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import com.example.doan2.service.ToadService;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.jsonObject.ToadJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/toadApi/")
public class ToadController {

    private int numberOfToadPerPage = 8;

    @Autowired
    ToadService toadService;

    @Autowired
    UserService userService;

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{userId}", method= RequestMethod.GET)
    public List<ToadJson> readAllToadOfUser(@PathVariable(value = "userId") int userId)  {
        User u = userService.findUserById(userId);
        System.out.println(u.toString());
        if (u != null)
        {
            List<Toad> toadList = toadService.findAllToadByOwner(u);
            List<ToadJson> listJson = new ArrayList<>();
            for (int i = 0; i < toadList.size(); i++) {
                ToadJson toadJson = new ToadJson();
                toadJson.CopyFromDataFromToad(toadList.get(i));
                toadJson.setListId(i);

                listJson.add(toadJson);
            }

            return listJson;
        }
        else
        {
            return null;
        }
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{userId}/page/{page}", method= RequestMethod.GET)
    public List<ToadJson> getToadPaginationOfThisPage(@PathVariable(value = "userId") int userId, @PathVariable(value= "page") int pageId)  {
        User u = userService.findUserById(userId);
        var toadList = toadService.findAllToadByOwner(u);
        List<ToadJson> listJson = new ArrayList<>();

        if (u != null)
        {

            int idMin = pageId * numberOfToadPerPage - numberOfToadPerPage;
            if (idMin < 0)
            {
                idMin = 0;
            }
            int idMax = pageId * numberOfToadPerPage;
            if(idMax > toadList.size())
            {
                idMax =  toadList.size();
            }

            for (int i = idMin; i < idMax; i++) {
                ToadJson toadJson = new ToadJson();
                toadJson.CopyFromDataFromToad(toadList.get(i));
                toadJson.setListId(i);

                listJson.add(toadJson);
            }

            return listJson;
        }
        else
        {
            return null;
        }
    }





}
