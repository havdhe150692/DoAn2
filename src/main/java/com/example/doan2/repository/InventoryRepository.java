package com.example.doan2.repository;


import com.example.doan2.entity.Inventory;
import com.example.doan2.entity.Toad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    Inventory findById(int id);
}
