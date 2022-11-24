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

    @ManyToOne
    @JoinColumn(name = "toad_ingame_id")
    private ToadIngame toadIngame;

    private int price;

    private Timestamp time;


    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }


}
