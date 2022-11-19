package com.example.doan2.repository;

import com.example.doan2.entity.Inventory;
import com.example.doan2.entity.ItemInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ItemInventoryRepository  extends JpaRepository<ItemInventory, Integer> {

    List<ItemInventory> findAllByOwnerInventory(Inventory inventory);
}
