package com.example.doan2.service;

import com.example.doan2.entity.Toad;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.ToadStatus;
import com.example.doan2.entity.User;
import com.example.doan2.repository.ToadRepository;
import com.example.doan2.repository.ToadStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToadService {

    @Autowired
    ToadRepository toadRepository;
    @Autowired
    ToadStatusRepository toadStatusRepository;

    public List<Toad> findAllToadByOwner(User user)
    {
        return toadRepository.findAllByOwner(user);
    }


    public Toad findToadDetail(int id)
    {
        return
                 toadRepository.findById(id);
    }

    public ToadStatus findByToadHolder(ToadIngame toadIngame)
    {
        return
                toadStatusRepository.findByToadIngame(toadIngame);
    }

}
