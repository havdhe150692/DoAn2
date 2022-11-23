package com.example.doan2.repository;


import com.example.doan2.entity.Toad;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;

@Repository
public interface ToadIngameRepository   extends JpaRepository<ToadIngame, Integer> {


    ToadIngame findAllByOwner(User owner);

    ToadIngame findById(int id);
}
