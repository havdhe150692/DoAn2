package com.example.doan2.service;

import com.example.doan2.entity.Market;

import java.util.List;

public interface MarketService {

    List<Market> findAll();

    Market findById(int id);

    Market saveMarket(Market market);
}
