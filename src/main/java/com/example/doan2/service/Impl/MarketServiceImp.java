package com.example.doan2.service.Impl;

import com.example.doan2.entity.Market;
import com.example.doan2.repository.MarketRepositoty;
import com.example.doan2.repository.ToadClassRepository;
import com.example.doan2.service.MarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
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
    public List<Market> findAllToad() {
        return marketRepo.findAllToad();
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
        List<Market> findToadBetweenPrice = marketRepo.findBetweenPrice(from, to);
        return findToadBetweenPrice;
    }

    @Override
    public List<Market> findByName(String name) {
        return marketRepo.findByName(name);
    }

    @Override
    public List<Market> findByNameContain(String name) {
        return marketRepo.findByNameContain(name);
    }

    @Override
    public List<Market> findByToadClass(int id) {
        return marketRepo.findByToadClass(id);
    }

    @Override
    public List<Market> findByRarity(int rarityNum) {
        return marketRepo.findByRarity(rarityNum);
    }

    @Override
    public int countToadByRarity(int countNum) {
        return marketRepo.countToad(countNum);
    }

    @Override
    public int countAllMarket() {
        return marketRepo.countAllMarket();
    }

    @Override
    public List<Market> sortFromHighestPrice() {
        return marketRepo.sortFromHighestPrice();
    }

    @Override
    public List<Market> sortFromLowestPrice() {
        return marketRepo.sortFromLowestPrice();
    }


    @Override
    public int countByToadClass(int id) {
        return marketRepo.countByToadClass(id);
    }

    @Override
    public Market findSellerToad(int toadId) {
        return marketRepo.findSellerToad(toadId);
    }

    @Override
    public Market findToadBySellerAtMarket(int toadId) {
        return marketRepo.findToadBySellerAtMarket(toadId);
    }

    @Transactional
    @Override
    public void cancelSellToadAtMarket(int toadIngameId) {
        marketRepo.cancelSellToadAtMarket(toadIngameId);
    }

    @Transactional
    @Override
    public void removeToadAtMarket(int toadIngameId) {
        marketRepo.removeToadAtMarket(toadIngameId);
    }


    @Override
    public Page<Market> pagingMarket(Pageable pageable) {
        List<Market> toads = marketRepo.findAllToad();
        if (!toads.isEmpty()) {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Market> listToad;
            if (toads.size() < startItem) {
                listToad = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, toads.size());
                listToad = toads.subList(startItem, toIndex);
            }
            Page<Market> toadPage = new PageImpl<Market>(listToad, PageRequest.of(currentPage, pageSize), toads.size());
            return toadPage;
        }
        return null;
    }

    @Override
    public Page<Market> customPaging(Pageable pageable, List<Market> toads) {
        if(!toads.isEmpty()) {
            int pageSize = pageable.getPageSize();
            int currentPage = pageable.getPageNumber();
            int startItem = currentPage * pageSize;
            List<Market> listToad;
            if (toads.size() < startItem) {
                listToad = Collections.emptyList();
            } else {
                int toIndex = Math.min(startItem + pageSize, toads.size());
                listToad = toads.subList(startItem, toIndex);
            }
            Page<Market> toadPage = new PageImpl<Market>(listToad, PageRequest.of(currentPage, pageSize), toads.size());
            return toadPage;
        }
        return null;
    }


}
