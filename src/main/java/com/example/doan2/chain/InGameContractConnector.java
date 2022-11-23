package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.User;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;

public class InGameContractConnector {

    private Web3j web3j = org.web3j.protocol.Web3j.build(new HttpService("http://192.168.171.128:8545"));
    private ToadKingToken playerToadKingToken;
    private User user;

    public InGameContractConnector(User user) throws Exception
    {
        this.user = user;
        playerToadKingToken = ToadKingToken.load(ServerContractInitiator.ToadKingToken_contractAddress,
                web3j,
                Credentials.create(user.getUserWallet().getPrivateKey()),
                BigInteger.ZERO, BigInteger.valueOf(182865));
        System.out.println("User " + user.getName() + " has " + playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send());

    }

    public BigInteger GetBalance() throws Exception {
        return playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send();
    }

    public void RequestMoney() throws Exception {
        ServerContractInitiator.TransferTokenFromCentral(user, 50000);
    }

}
