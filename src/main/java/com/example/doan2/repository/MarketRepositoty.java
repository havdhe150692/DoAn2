package com.example.doan2.repository;

import com.example.doan2.entity.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepositoty extends JpaRepository<Market, Integer> {

    List<Market> findAll();

    Market findById(int id);

    @Query(
            value = "SELECT * FROM market m WHERE m.price BETWEEN :from AND :to" , nativeQuery = true
    )
    List<Market> findBetweenPrice(@Param("from") int from, @Param("to") int to);
}
