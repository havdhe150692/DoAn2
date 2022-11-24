package com.example.doan2.entity;

import com.example.doan2.utils.Enum;

import javax.persistence.*;
import java.sql.Date;


public class Toad {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private String name;

    private Enum.Rarity rarity;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;


    @Column(name="info",columnDefinition="LONGTEXT")
    private String info;

    private Date dateOfBirth;


    @ManyToOne
    @JoinColumn(name = "toad_class_id")
    private ToadClass toadClass;

//    @OneToOne(cascade = CascadeType.ALL, mappedBy = "toad")
//    @PrimaryKeyJoinColumn
//    private ToadStatus toadStatus;


    private int pictureId;

    public int getPictureId() {
        return pictureId;
    }

    public void setPictureId(int pictureId) {
        this.pictureId = pictureId;
    }

//    public ToadStatus getToadStatus() {
//        return toadStatus;
//    }
//
//    public void setToadStatus(ToadStatus toadStatus) {
//        this.toadStatus = toadStatus;
//    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ToadClass getToadClass() {
        return toadClass;
    }

    public void setToadClass(ToadClass toadClass) {
        this.toadClass = toadClass;
    }

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
