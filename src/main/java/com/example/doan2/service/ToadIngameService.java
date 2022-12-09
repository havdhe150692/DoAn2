package com.example.doan2.service;

import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;

import java.util.List;

public interface ToadIngameService {

    ToadIngame findById(int id);

    List<ToadIngame> findAllToadByOwner(User owner);

    List<ToadClass> findAllToadClass();

    List<ToadIngame> findUserToadByRarity(int rarityNum,int userId);

    int countAllUserToad(int userId);

    int countUserToadByRarity(int userId, int rarityNum);

    List<ToadIngame> findUserToadByName(String name, int userId);

    void changeToadOwner(int ownerId, int toadId);
}
