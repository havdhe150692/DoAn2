package com.example.doan2.repository;



import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToadIngameRepository   extends JpaRepository<ToadIngame, Integer> {



    List<ToadIngame> findAllByOwner(User owner);

    List<ToadIngame> findAllToadByOwner(User owner);

    ToadIngame findById(int id);

}
