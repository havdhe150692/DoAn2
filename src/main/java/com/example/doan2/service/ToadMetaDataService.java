package com.example.doan2.service;


import com.example.doan2.entity.ToadData;
import com.example.doan2.repository.ToadDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToadMetaDataService {

    @Autowired
    ToadDataRepository toadDataRepository;


    public ToadData generateNewToadFrom()
    {
        return null;
    }
}
