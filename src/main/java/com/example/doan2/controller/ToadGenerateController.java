package com.example.doan2.controller;

import com.example.doan2.entity.ToadData;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.ToadStatus;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.ToadPoolRepository;
import com.example.doan2.service.ToadCreationService;
import com.example.doan2.service.ToadStatusLogicService;
import com.example.doan2.service.ToadURIService;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.Enum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;


import java.sql.Date;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/toadGeneration/")
public class ToadGenerateController {

    @Autowired
    ToadURIService toadURIService;

    @Autowired
    ToadCreationService toadCreationService;

    @Autowired
    UserService userService;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    ToadPoolRepository toadPoolRepository;

    @Autowired
    ToadStatusLogicService toadStatusLogicService;

    @CrossOrigin(origins = "http://localhost:8000")
    @RequestMapping(value ="/checkRarity", method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE )
    public @ResponseBody List<String> CheckRarity(String rarityA, String rarityB) throws Exception {
        Enum.Rarity rarityAvar = Enum.Rarity.valueOf(rarityA);
        Enum.Rarity rarityBvar = Enum.Rarity.valueOf(rarityB);

        return toadCreationService.GenerateRarityCheck(rarityAvar, rarityBvar);
    }

    @CrossOrigin(origins = {"http://localhost:8000", "https://web.postman.co"})
    @RequestMapping(value ="/commonToad", method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ToadIngame CreateACommonToad(int userId)
    {
        User u = userService.findUserById(userId);
        ToadData toadData = toadCreationService.GenerateACommonToad();

        ToadIngame toadIngame = new ToadIngame();
        toadIngame.setToadData(toadData);
        toadIngame.setOwner(u);

        Instant instant = Instant.now();
        Timestamp timestamp = Timestamp.from(instant);
        System.out.print("The time is " + timestamp);

        toadIngame.setDateOfBirth(timestamp);
        toadIngame.setTypeCounter(0);
        ToadStatus toadStatus = toadStatusLogicService.StatusGeneration(toadData);
        toadIngame.setToadStatus(toadStatus);
        toadStatus.setToadIngame(toadIngame);

        toadIngameRepository.save(toadIngame);

        return toadIngame;
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @RequestMapping(value ="/toadBreedingOld", method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ToadIngame BreedAToad(int userId, String rarityA, String rarityB) throws Exception {
        User u = userService.findUserById(userId);
        Enum.Rarity rarityAvar = Enum.Rarity.valueOf(rarityA);
        Enum.Rarity rarityBvar = Enum.Rarity.valueOf(rarityB);


        ToadData toadData = toadCreationService.GenerateNewToadFromParent(rarityAvar, rarityBvar);
        ToadIngame toadIngame = toadCreationService.GenerateIngameToadFromToadData(u, toadData);


        return toadIngame;
    }

    @CrossOrigin(origins = "http://localhost:8000")
    @RequestMapping(value ="/toadBreeding", method= RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public @ResponseBody ToadIngame BreedAToadWithId(int userId, int parentAId,  int parentBId) throws Exception {
        User u = userService.findUserById(userId);
        ToadIngame t1 = toadIngameRepository.findById(parentAId);
        ToadIngame t2 = toadIngameRepository.findById(parentBId);
        if (u != null && t1 != null && t2 != null) {
            Instant inst = Instant.now();
            if ((inst.compareTo(t1.getToadStatus().getExpectedBreed().toInstant()) >= 0) &&
                    (inst.compareTo(t2.getToadStatus().getExpectedBreed().toInstant()) >= 0)) {

                ToadData toadData = toadCreationService.GenerateNewToadFromParent(t1.getToadData().getRarity(), t2.getToadData().getRarity());
                ToadIngame toadIngame = toadCreationService.GenerateIngameToadFromToadData(u, toadData);

                toadStatusLogicService.ToadBreed(t1);
                toadStatusLogicService.ToadBreed(t2);
                return toadIngame;
            }
        } return null;
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/toad/{toadId}", method= RequestMethod.DELETE)
    public boolean deleteToad(@PathVariable(value = "toadId") int toadId)  {
       toadIngameRepository.delete(toadIngameRepository.findById(toadId));
       return true;
    }
}