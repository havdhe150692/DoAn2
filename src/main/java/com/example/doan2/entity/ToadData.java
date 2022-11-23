package com.example.doan2.entity;


import com.example.doan2.utils.Enum;

import javax.persistence.*;

@Entity
public class ToadData {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private Enum.Rarity rarity;

    @Column(name="info",columnDefinition="LONGTEXT")
    private String info;

    private int pictureId;

    @ManyToOne
    @JoinColumn(name = "toad_class_id")
    private ToadClass toadClass;

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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

    public ToadClass getToadClass() {
        return toadClass;
    }

    public void setToadClass(ToadClass toadClass) {
        this.toadClass = toadClass;
    }
}
