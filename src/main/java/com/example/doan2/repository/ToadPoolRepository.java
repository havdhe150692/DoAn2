package com.example.doan2.repository;

import com.example.doan2.entity.ToadData;
import com.example.doan2.entity.ToadPool;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;


@Repository
public interface ToadPoolRepository extends JpaRepository<ToadPool, Integer> {

    ToadPool findById(int id);
    ToadPool findByToadData(ToadData toadData);
}
