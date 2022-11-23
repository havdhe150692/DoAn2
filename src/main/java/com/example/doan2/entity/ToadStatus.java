package com.example.doan2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class ToadStatus {

    @Id
    private int id;


    @JsonIgnore
    @OneToOne
    @MapsId
    private ToadIngame toadIngame;


    private Timestamp expectedMature;

    private Timestamp expectedBreed;

    private Timestamp expectedHungry;

    private Timestamp expectedCollect;


    public Timestamp getExpectedMature() {
        return expectedMature;
    }

    public void setExpectedMature(Timestamp expectedMature) {
        this.expectedMature = expectedMature;
    }

    public Timestamp getExpectedBreed() {
        return expectedBreed;
    }

    public void setExpectedBreed(Timestamp expectedBreed) {
        this.expectedBreed = expectedBreed;
    }

    public Timestamp getExpectedHungry() {
        return expectedHungry;
    }

    public void setExpectedHungry(Timestamp expectedHungry) {
        this.expectedHungry = expectedHungry;
    }

    public Timestamp getExpectedCollect() {
        return expectedCollect;
    }

    public void setExpectedCollect(Timestamp expectedCollect) {
        this.expectedCollect = expectedCollect;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }




}
