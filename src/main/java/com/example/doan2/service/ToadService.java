package com.example.doan2.service;

import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToadService {

    @Autowired
    ToadRepository toadRepository;

    List<Toad> findAllToadByOwner(User user)
    {
        return toadRepository.findAllByOwner(user);
    }
}
