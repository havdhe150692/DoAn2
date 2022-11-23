package com.example.doan2.utils.randomRarity;

import com.example.doan2.utils.Enum;
import org.bouncycastle.its.asn1.IValue;

public class ValueEntry {
    public Enum.Rarity rarityType;
    public int value; //0-10000


    public ValueEntry(Enum.Rarity rarity, int value)
    {
        this.rarityType = rarity;
        this.value =value;
    }
}
