package com.example.doan2.service;

import com.example.doan2.entity.Market;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarketService {

    List<Market> findAll();

    List<Market> findAllToad();

    Market findById(int id);

    Market saveMarket(Market market);

    List<Market> findBetweenPrice(int from , int to);

    List<Market> findByName(String name);

    List<Market> findByNameContain(String name);

    List<Market> findByToadClass(int id);

    List<Market> findByRarity(int rarityNum);

    int countToadByRarity(int countNum);

    int countAllMarket();

    List<Market> sortFromHighestPrice();

    List<Market> sortFromLowestPrice();

    int countByToadClass(int id);

    Market findSellerToad(int toadId);

    Market findToadBySellerAtMarket(int toadId);


    void cancelSellToadAtMarket(int toadIngameId);

    void removeToadAtMarket(int toadIngameId);

    Page<Market> pagingMarket(Pageable pageable);

    Page<Market> customPaging(Pageable pageable, List<Market> customList);
}
