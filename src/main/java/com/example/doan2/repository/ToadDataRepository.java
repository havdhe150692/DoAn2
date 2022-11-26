package com.example.doan2.repository;

import com.example.doan2.entity.ToadData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ToadDataRepository extends JpaRepository<ToadData, Integer> {

    ToadData findById(int id);


}
