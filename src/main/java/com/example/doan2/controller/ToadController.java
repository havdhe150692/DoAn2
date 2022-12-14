package com.example.doan2.controller;



import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.ToadStatus;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.service.AdminContractExecutionService;
import com.example.doan2.service.ToadStatusLogicService;
import com.example.doan2.service.UserService;
import com.example.doan2.utils.jsonObject.ToadDetailJson;
import com.example.doan2.utils.jsonObject.ToadListJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/toadApi/")
public class ToadController {

    private int numberOfToadPerPage = 6;



    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    UserService userService;

    @Autowired
    ToadStatusLogicService toadStatusLogicService;

    @Autowired
    AdminContractExecutionService adminContractExecutionService;



//    @CrossOrigin(origins ="http://localhost:8000")
//    @RequestMapping(value ="/old/{userId}", method= RequestMethod.GET)
//    public List<ToadListJson> readAllToadOfUser(@PathVariable(value = "userId") int userId)  {
//        User u = userService.findUserById(userId);
//        if (u != null)
//        {
//            List<Toad> toadList = toadService.findAllToadByOwner(u);
//            List<ToadListJson> listJson = new ArrayList<>();
//            for (int i = 0; i < toadList.size(); i++) {
//                ToadListJson toadListJson = new ToadListJson();
//                toadListJson.CopyFromDataFromToad(toadList.get(i));
//                toadListJson.setListId(i);
//
//                listJson.add(toadListJson);
//            }
//
//            return listJson;
//        }
//        else
//        {
//            return null;
//        }
//    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/{userId}", method= RequestMethod.GET)
    public List<ToadIngame> readAllToadIngameOfUser(@PathVariable(value = "userId") int userId) throws JSONException {
        User u = userService.findUserById(userId);
        if (u != null)
        {
            List<ToadIngame> toadList = toadIngameRepository.findAllByOwner(u);
            for (int i = 0; i < toadList.size(); i++) {
                ToadIngame toadIngame =  toadList.get(i);
                toadStatusLogicService.StatusCheck(toadIngame.getToadStatus());

            }

            return toadList;
        }
        else
        {
            return null;
        }
    }


    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/status/{userId}", method= RequestMethod.GET)
    public String readAllToadStatus(@PathVariable(value = "userId") int userId) throws JSONException {
        User u = userService.findUserById(userId);
        if (u != null)
        {
            List<ToadIngame> toadList = toadIngameRepository.findAllByOwner(u);
            JSONArray jsonArray = new JSONArray();
            for (int i = 0; i < toadList.size(); i++) {
                ToadIngame toadIngame =  toadList.get(i);
                ToadStatus toadStatus = toadIngame.getToadStatus();
                jsonArray.put(toadStatusLogicService.StatusCheck(toadStatus));
            }

            return jsonArray.toString();
        }
        else
        {
            return null;
        }
    }

//    @CrossOrigin(origins ="http://localhost:8000")
//    @RequestMapping(value ="/detail/{toadId}", method= RequestMethod.GET)
//    public ToadDetailJson getDetailOfThisToad(@PathVariable(value = "toadId") int toadId)
//    {
//        Toad t = toadService.findToadDetail(toadId);
//        ToadDetailJson tJson = new ToadDetailJson();
//        tJson.CopyFromDataFromToad(t);
//        return tJson;
//    }

    @CrossOrigin(origins ="http://localhost:8000")
    @RequestMapping(value ="/toadNFT/count/{userId}", method= RequestMethod.GET)
    public BigInteger countNFT(@PathVariable(value = "userId") int userId) throws Exception {
        User u = userService.findUserById(userId);


        return  adminContractExecutionService.CountNFT(u);
    }



//    @CrossOrigin(origins ="http://localhost:8000")
//    @RequestMapping(value ="/user/{userId}/status", method= RequestMethod.GET)
//    public List<ToadStatus> getAllStatusOfToadOfThisUser(@PathVariable(value = "userId") int userId)
//    {
//        List<ToadStatus> toadStatuses = new ArrayList<>();
//        User u = userService.findUserById(userId);
//        List<Toad> toadList = toadService.findAllToadByOwner(u);
//        for (Toad t: toadList) {
//            toadStatuses.add(toadService.findByToadHolder(t));
//        }
//
//        return toadStatuses;
//    }

//    @CrossOrigin(origins ="http://localhost:8000")
//    @RequestMapping(value ="/status/{toadId}", method= RequestMethod.GET)
//    public ToadStatus getStatusOfThisToad(@PathVariable(value = "toadId") int toadId)
//    {
//        Toad t = toadService.findToadDetail(toadId);
//        return toadService.findByToadHolder(t);
//    }


//    @CrossOrigin(origins ="http://localhost:8000")
//    @RequestMapping(value ="/{userId}/page/{page}", method= RequestMethod.GET)
//    public List<ToadListJson> getToadPaginationOfThisPage(@PathVariable(value = "userId") int userId, @PathVariable(value= "page") int pageId)  {
//        User u = userService.findUserById(userId);
//        var toadList = toadService.findAllToadByOwner(u);
//        List<ToadListJson> listJson = new ArrayList<>();
//        if (u != null)
//        {
//            int idMin = pageId * numberOfToadPerPage - numberOfToadPerPage;
//            if (idMin < 0)
//            {
//                idMin = 0;
//            }
//            int idMax = pageId * numberOfToadPerPage;
//            if(idMax > toadList.size())
//            {
//                idMax =  toadList.size();
//            }
//
//            for (int i = idMin; i < idMax; i++) {
//                ToadListJson toadListJson = new ToadListJson();
//                toadListJson.CopyFromDataFromToad(toadList.get(i));
//                toadListJson.setListId(i);
//
//                listJson.add(toadListJson);
//            }
//
//            return listJson;
//        }
//        else
//        {
//            return null;
//        }
//    }





}
