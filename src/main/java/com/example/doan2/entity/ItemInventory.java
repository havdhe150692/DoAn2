package com.example.doan2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class ItemInventory {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private ItemShop itemShop;

    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "owner_inventory_user_id")
    private Inventory ownerInventory;




    public Inventory getOwnerInventory() {
        return ownerInventory;
    }

    public void setOwnerInventory(Inventory ownerInventory) {
        this.ownerInventory = ownerInventory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ItemShop getItemShop() {
        return itemShop;
    }

    public void setItemShop(ItemShop itemShop) {
        this.itemShop = itemShop;
    }


}
