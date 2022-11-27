package com.example.doan2.entity;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
public class ToadIngame {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "toad_data_id")
    private ToadData toadData;


    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private int typeCounter;
    private Timestamp dateOfBirth;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "toadIngame")
    @PrimaryKeyJoinColumn
    private ToadStatus toadStatus;

    public int getTypeCounter() {
        return typeCounter;
    }

    public void setTypeCounter(int typeCounter) {
        this.typeCounter = typeCounter;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Timestamp getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Timestamp dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public ToadStatus getToadStatus() {
        return toadStatus;
    }

    public void setToadStatus(ToadStatus toadStatus) {
        this.toadStatus = toadStatus;
    }

    public ToadData getToadData() {
        return toadData;
    }

    public void setToadData(ToadData toadData) {
        this.toadData = toadData;
    }
}
