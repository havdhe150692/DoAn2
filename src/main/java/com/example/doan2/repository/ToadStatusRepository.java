package com.example.doan2.repository;


import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.ToadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToadStatusRepository   extends JpaRepository<ToadStatus, Integer> {


    ToadStatus findByToadIngame (ToadIngame t);


}
