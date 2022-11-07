package com.example.doan2.utils.jsonObject;

import com.example.doan2.entity.Toad;
import com.example.doan2.utils.Enum;

import java.text.SimpleDateFormat;

public class ToadDetailJson {

    private int globalId;
    private String name;
    private Enum.Rarity rarity;
    private String dateOfBirth;
    private String info;
    private String toadClass;


    public void CopyFromDataFromToad(Toad toad)
    {
        this.globalId = toad.getId();
        this.name = toad.getName();
        this.rarity = toad.getRarity();

        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");// FOrmat in This Format or you change Change as well
        this.dateOfBirth = format.format(toad.getDateOfBirth());
        this.info = toad.getInfo();
        this.toadClass = toad.getToadClass().getName();
    }

    public int getGlobalId() {
        return globalId;
    }

    public void setGlobalId(int globalId) {
        this.globalId = globalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum.Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Enum.Rarity rarity) {
        this.rarity = rarity;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getToadClass() {
        return toadClass;
    }

    public void setToadClass(String toadClass) {
        this.toadClass = toadClass;
    }
}
