package com.example.doan2.utils.jsonObject;

import com.example.doan2.utils.Enum;

public class ToadListJson {

    private int ListId;
    private int globalId;
    private String name;
    private Enum.Rarity rarity;
    private int ownerId;


//    public void CopyFromDataFromToad(Toad toad)
//    {
//        this.globalId = toad.getId();
//        this.name = toad.getName();
//        this.rarity = toad.getRarity();
//        this.ownerId = toad.getOwner().getId();
//    }

    public int getListId() {
        return ListId;
    }

    public void setListId(int listId) {
        ListId = listId;
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

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }
}
