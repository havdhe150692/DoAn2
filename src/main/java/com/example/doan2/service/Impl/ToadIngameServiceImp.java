package com.example.doan2.service.Impl;

import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadClassRepository;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.service.ToadIngameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ToadIngameServiceImp implements ToadIngameService {

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Autowired
    ToadClassRepository toadClassRepository;

    @Override
    public ToadIngame findById(int id) {
        return toadIngameRepository.findById(id);
    }

    @Override
    public List<ToadIngame> findAllToadByOwner(User owner) {
        return toadIngameRepository.findAllToadByOwner(owner);
    }

    @Override
    public List<ToadClass> findAllToadClass() {
        return toadClassRepository.findAll();
    }

    @Override
    public List<ToadIngame> findUserToadByRarity(int rarityNum, int userId) {
        return toadIngameRepository.findUserToadByRarity(rarityNum,userId);
    }

    @Override
    public int countAllUserToad(int userId) {
        return toadIngameRepository.countAllUserToad(userId);
    }

    @Override
    public int countUserToadByRarity(int userId, int rarityNum) {
        return toadIngameRepository.countUserToadByRarity(userId, rarityNum);
    }

    @Override
    public List<ToadIngame> findUserToadByName(String name, int userId) {
        return toadIngameRepository.findUserToadByName(name, userId);
    }

    @Transactional
    @Override
    public void changeToadOwner(int ownerId, int toadId) {
        toadIngameRepository.changeToadOwner(ownerId, toadId);
    }
}
