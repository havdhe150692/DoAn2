package com.example.doan2.repository;

import com.example.doan2.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRepositoty extends JpaRepository<Market, Integer> {

}
