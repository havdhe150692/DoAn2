package com.example.doan2.service.Impl;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.repository.MarketRepositoty;
import com.example.doan2.repository.ToadClassRepository;
import com.example.doan2.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketServiceImp implements MarketService {
    @Autowired
    MarketRepositoty marketRepo;

    @Autowired
    ToadClassRepository toadClassRepo;


    @Override
    public List<Market> findAll() {
        return marketRepo.findAll();
    }

    @Override
    public Market findById(int id) {
        return marketRepo.findById(id);
    }

    @Override
    public Market saveMarket(Market market) {
        return marketRepo.save(market);
    }

    @Override
    public List<Market> findBetweenPrice(int from, int to) {
        List<Market> findToadBetweenPrice = marketRepo.findBetweenPrice(from,to);
        return findToadBetweenPrice;
    }

    @Override
    public List<Market> findByClass(Integer id) {
        return marketRepo.findByToadClass(id);
    }


}