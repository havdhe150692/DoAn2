package com.example.doan2.repository;

import com.example.doan2.entity.ToadData;
import com.example.doan2.utils.Enum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ToadDataRepository extends JpaRepository<ToadData, Integer> {

    ToadData findById(int id);

    List<ToadData> findAllByRarity(Enum.Rarity common);
}
