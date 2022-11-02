package com.example.doan2.repository;

import com.example.doan2.entity.Toad;
import com.example.doan2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  ToadRepository  extends JpaRepository<Toad, Integer> {


    List<Toad> findAllByOwner(User owner);
    int countByOwner(User owner);

    Toad findById(int id);
}
