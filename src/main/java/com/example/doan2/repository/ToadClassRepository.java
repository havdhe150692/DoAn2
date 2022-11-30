package com.example.doan2.repository;
import com.example.doan2.entity.ToadClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ToadClassRepository extends JpaRepository<ToadClass,Integer> {

    List<ToadClass> findAll();
}
