package com.example.doan2.entity;


import javax.persistence.*;

@Entity
public class ToadPool {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    private int totalAmount;

    private int currentIndex;

    @ManyToOne
    @JoinColumn(name = "toad_meta_data_id")
    private ToadData toadData;

    public ToadData getToadData() {
        return toadData;
    }

    public void setToadData(ToadData toadMetaData) {
        this.toadData = toadMetaData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalNumber) {
        this.totalAmount = totalNumber;
    }

    public int getCurrentIndex() {
        return currentIndex;
    }

    public void setCurrentIndex(int currentIndex) {
        this.currentIndex = currentIndex;
    }
}
