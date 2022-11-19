package com.example.doan2.entity;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    @OneToOne
    @JoinColumn(name = "toad_id")
    private Toad toad;

    private int price;

    private Timestamp time;

    public Toad getToad() {
        return toad;
    }

    public void setToad(Toad toad) {
        this.toad = toad;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }


}
