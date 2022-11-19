package com.example.doan2.chain;

import org.web3j.crypto.*;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.io.File;

public class ContractAccountRegister {


    public ContractAccountRegister() throws Exception {
        Start();
    }

    public void Start() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:20000"));
        ECKeyPair keyPair = Keys.createEcKeyPair();
        WalletFile wallet = Wallet.createStandard("", keyPair);

        System.out.println("Private key: " + keyPair.getPrivateKey().toString(16));
        System.out.println("Account: " + wallet.getAddress());

//        String fileName = WalletUtils.generateNewWalletFile(
//                "",
//                new File("./"));

        File directory = new File("./");
        System.out.println(directory.getAbsolutePath());

//        Credentials credentials = WalletUtils.loadCredentials(
//                "",
//                "./");

  //      System.out.println(credentials);
    }



}
