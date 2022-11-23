package com.example.doan2;

import com.example.doan2.chain.ServerContractInitiator;
import com.example.doan2.utils.randomRarity.RandomRarityGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoAn2Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(DoAn2Application.class, args);
        ServerContractInitiator serverContractInitiator = new ServerContractInitiator();


    }
}
