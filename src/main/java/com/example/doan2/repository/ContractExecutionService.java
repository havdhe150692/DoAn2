package com.example.doan2.repository;

import com.example.doan2.chain.ServerContractInitiator;
import com.example.doan2.chain.smartcontract.ToadKingNFT;
import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.ToadURIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import java.math.BigInteger;

@Service
public class ContractExecutionService {

    @Autowired
    ToadURIService toadURIService;

    private ToadKingToken toadKingToken = ServerContractInitiator.adminToadKingToken;
    private ToadKingNFT toadKingNFT = ServerContractInitiator.adminToadKingNFT;

    public  void TransferTokenFromCentral(User toUser, int amount) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        System.out.println(toadKingToken.transfer(address, BigInteger.valueOf(amount))
                .send().getTransactionHash());

    }

    public void MintNFTFromCentral(User toUser, ToadIngame toadIngame) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        String uriAddress = "localhost:8080/uriApi/" + toadIngame.getId();
        var transaction = toadKingNFT.safeMint(address, BigInteger.valueOf(toadIngame.getId()), uriAddress).send();
        System.out.print(transaction.getTransactionHash());



    }

}
