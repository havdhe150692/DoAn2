package com.example.doan2.utils.randomRarity;

import com.example.doan2.utils.Enum;

import java.util.List;

public class RarityLookup {
    public Enum.Rarity combineA;
    public Enum.Rarity combineB;
    public List<ValueEntry> entries;

    public RarityLookup(Enum.Rarity combineA, Enum.Rarity combineB, List<ValueEntry> entries) {
        this.combineA = combineA;
        this.combineB = combineB;
        this.entries = entries;
    }
}
