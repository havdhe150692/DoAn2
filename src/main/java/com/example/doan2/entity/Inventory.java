package com.example.doan2.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventory {

    @Id
    @Column(name = "user_id")
    private int id;

    @JsonIgnore
    @MapsId
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int tokenQuantity;


    public Inventory() {
        tokenQuantity = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getToken() {
        return tokenQuantity;
    }

    public void setToken(int token) {
        this.tokenQuantity = token;
    }
}
