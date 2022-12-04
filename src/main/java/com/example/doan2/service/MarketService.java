package com.example.doan2.service;

import com.example.doan2.entity.Market;
import com.example.doan2.entity.ToadClass;
import com.example.doan2.entity.ToadIngame;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarketService {

    List<Market> findAll();

    Market findById(int id);

    Market saveMarket(Market market);

    List<Market> findBetweenPrice(int from , int to);

    List<Market> findByClass(Integer id);
}
