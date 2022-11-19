package com.example.doan2.controller;


import com.example.doan2.entity.ItemInventory;
import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import com.example.doan2.service.InventoryService;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.jsonObject.ToadListJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/inventoryApi/")
public class InventoryController {

    @Autowired
    InventoryService inventoryService;

    @Autowired
    UserService userService;


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{userId}", method= RequestMethod.GET)
    public List<ItemInventory> readAllInventoryOfUser(@PathVariable(value = "userId") int userId)  {
        return inventoryService.findItemInventoryListByUserId(userId);
    }
}
