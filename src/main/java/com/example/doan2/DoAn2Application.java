package com.example.doan2;

import com.example.doan2.chain.ContractAccountRegister;
import com.example.doan2.chain.ContractConnectorPublic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DoAn2Application {

    public static void main(String[] args) throws Exception {

        SpringApplication.run(DoAn2Application.class, args);
        //ContractConnectorPublic connectorPublic = new ContractConnectorPublic();
        //ContractAccountRegister contractAccountRegister = new ContractAccountRegister();
    }
}
