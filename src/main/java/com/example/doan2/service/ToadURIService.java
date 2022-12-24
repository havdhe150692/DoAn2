package com.example.doan2.service;

import com.example.doan2.entity.ToadData;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.repository.ToadDataRepository;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.repository.ToadPoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class ToadURIService {

    @Autowired
    ToadPoolRepository toadPoolRepository;

    @Autowired
    ToadDataRepository toadDataRepository;

    @Autowired
    ToadIngameRepository toadIngameRepository;

    public JSONObject ReturnToadURI(ToadIngame toadIngame) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", toadIngame.getToadData().getName());
        jsonObject.put("class", toadIngame.getToadData().getToadClass().getName());
        jsonObject.put("rarity", toadIngame.getToadData().getRarity().toString());
        jsonObject.put("info", toadIngame.getToadData().getInfo());
        jsonObject.put("dob", toadIngame.getDateOfBirth().toString());
        jsonObject.put("img", "localhost:8080/toad_img/" + toadIngame.getToadData().getPictureId()+".png");
        jsonObject.put("typeId",toadIngame.getTypeCounter());
        return  jsonObject;
    }

    public ToadIngame FindToadById(int id)
    {
        return toadIngameRepository.findById(id);
    }
}
