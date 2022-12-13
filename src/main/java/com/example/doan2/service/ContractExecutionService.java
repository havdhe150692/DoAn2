package com.example.doan2.service;

import com.example.doan2.chain.ServerContractInitiator;
import com.example.doan2.chain.UserContractConnector;
import com.example.doan2.chain.smartcontract.ToadKingMarketplace;
import com.example.doan2.chain.smartcontract.ToadKingNFT;
import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import com.example.doan2.service.ToadURIService;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

import java.math.BigInteger;

@Service
public class ContractExecutionService {

    @Autowired
    ToadURIService toadURIService;



    private ToadKingToken toadKingToken = ServerContractInitiator.adminToadKingToken;
    private ToadKingNFT toadKingNFT = ServerContractInitiator.adminToadKingNFT;

    private ToadKingMarketplace toadKingMarketplace = ServerContractInitiator.adminToadKingMarketplace;

    public  void TransferTokenFromCentral(User toUser, int amount) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        System.out.println(toadKingToken.transfer(address, BigInteger.valueOf(amount))
                .send().getTransactionHash());


    }

    public void TransferFromImplementationExecution(User fromUser, User toUser, int amount) throws  Exception
    {
        String address1 = fromUser.getUserWallet().getAddress();
        String address2 = toUser.getUserWallet().getAddress();
        UserContractConnector userContractConnector = new UserContractConnector(fromUser);
        System.out.println("address1 = " + address1);
        System.out.println("address2 = " + address1);
        System.out.println("host = " + ServerContractInitiator.hostAccountAddress);
        //
        System.out.println("Approve is "
                + userContractConnector.playerToadKingToken.approve((
                        Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress()),
                        BigInteger.valueOf(amount)).send());

        System.out.println("Allowance is "
                + userContractConnector.playerToadKingToken.allowance(
                        address1,
                        Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress()).send());

        System.out.println(ServerContractInitiator.adminToadKingToken.
                        transferFrom(address1, address2, BigInteger.valueOf(amount)).send());


    }

    public void MintNFTFromCentral(User toUser, ToadIngame toadIngame) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        String uriAddress = "localhost:8080/uriApi/" + toadIngame.getId();
        var transaction = toadKingNFT.safeMint(address, BigInteger.valueOf(toadIngame.getId()), uriAddress).send();
        System.out.print(transaction.getTransactionHash());

    }

    public BigInteger CountNFT(User toUser) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        var transaction = toadKingNFT.balanceOf(address).send();
        System.out.print(transaction);
        return transaction;

    }

    public String CheckNFT(int tokenId) throws Exception {
        String tokenURI = toadKingNFT.tokenURI(BigInteger.valueOf(tokenId)).send();
        String owner = toadKingNFT.ownerOf(BigInteger.valueOf(tokenId)).send();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("tokenURI", tokenURI);
        jsonObject.put("owner", owner);

        return jsonObject.toString();

    }

}
