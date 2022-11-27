package com.example.doan2.repository;


import com.example.doan2.entity.Toad;
import com.example.doan2.entity.ToadData;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Entity;
import java.util.List;

@Repository
public interface ToadIngameRepository   extends JpaRepository<ToadIngame, Integer> {


    ToadIngame findById(int id);

    ToadIngame findByToadDataAndTypeCounter(ToadData toadData, int typeCounter);

    List<ToadIngame> findAllByOwner(User owner);
}
