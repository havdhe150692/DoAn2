package com.example.doan2.entity;

import com.example.doan2.utils.Enum;

import javax.persistence.*;

@Entity
public class Toad {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private Enum.Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum.Rarity getRarity() {
        return rarity;
    }

    public void setRarity(Enum.Rarity rarity) {
        this.rarity = rarity;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
