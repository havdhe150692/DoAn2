package com.example.doan2.controller;


import com.example.doan2.entity.ItemInventory;
import com.example.doan2.service.ContractExecutionService;
import com.example.doan2.service.ToadCreationService;
import com.example.doan2.service.ToadURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/uriApi/")
public class ToadURIController {


    @Autowired
    ToadURIService toadURIService;

    @Autowired
    ToadCreationService toadCreationService;

    @Autowired
    ContractExecutionService contractExecutionService;

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{id}", method= RequestMethod.GET)
    public String readToadURI(@PathVariable(value = "id") int toadId) throws JSONException {

        return toadURIService.ReturnToadURI(toadURIService.FindToadById(toadId)).toString();
    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/metadata/{id}", method= RequestMethod.GET)
    public String readToadMetaData(@PathVariable(value = "id") int toadId) throws Exception {

        return contractExecutionService.CheckNFT(toadId);
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/test", method= RequestMethod.GET)
    public void test() throws JSONException {

        toadCreationService.Test(20);
    }
}
