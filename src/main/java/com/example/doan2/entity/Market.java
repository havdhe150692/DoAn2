package com.example.doan2.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

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


    private int isSelling = 0;



    @JsonFormat(timezone = "Asia/Ho_Chi_Minh")
    private Timestamp time;


    public int isSelling() {
        return isSelling;
    }

    public void setSelling(int selling) {
        isSelling = selling;
    }

    public User getSeller() {
        return seller;
    }

    public int getId() {
        return id;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public ToadIngame getToadIngame() {
        return toadIngame;
    }

    public void setToadIngame(ToadIngame toadIngame) {
        this.toadIngame = toadIngame;
    }

}
