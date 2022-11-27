package com.example.doan2.service.Impl;

import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadIngameRepository;
import com.example.doan2.service.ToadIngameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToadIngameServiceImp implements ToadIngameService {

    @Autowired
    ToadIngameRepository toadIngameRepository;

    @Override
    public ToadIngame findById(int id) {
        return toadIngameRepository.findById(id);
    }

    @Override
    public List<ToadIngame> findAllToadByOwner(User owner) {
        return toadIngameRepository.findAllToadByOwner(owner);
    }
}
