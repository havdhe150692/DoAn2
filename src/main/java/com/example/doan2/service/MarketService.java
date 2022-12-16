package com.example.doan2.service;

import com.example.doan2.entity.Market;
import jnr.ffi.annotations.In;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MarketService {

    List<Market> findAll();

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

    Page<Market> pagingMarket(Pageable pageable) throws Exception;

    Page<Market> customPaging(Pageable pageable, List<Market> customList);

    Market findByToadInGameId (Integer id);

    List<Market> findByListToadIngameId(List<Integer> id);

    void updateMarketId(Integer newId, Integer oldId);

    void deleteById(Integer id);
}
