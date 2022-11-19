package com.example.doan2.service;


import com.example.doan2.entity.Inventory;
import com.example.doan2.entity.ItemInventory;
import com.example.doan2.repository.InventoryRepository;
import com.example.doan2.repository.ItemInventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {

    @Autowired
    InventoryRepository inventoryService;

    @Autowired
    ItemInventoryRepository itemInventoryRepository;


    public List<ItemInventory> findItemInventoryListByUserId(int userId)
    {
        Inventory i = inventoryService.findById(userId);
        return itemInventoryRepository.findAllByOwnerInventory(i);
    }
}
