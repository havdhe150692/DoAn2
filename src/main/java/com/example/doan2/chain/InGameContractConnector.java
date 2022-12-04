package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.User;
import com.example.doan2.service.ContractExecutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.spring.autoconfigure.Web3jAutoConfiguration;

import java.math.BigInteger;

public class InGameContractConnector {

    private Web3j web3j = ServerContractInitiator.web3j;

    private ToadKingToken playerToadKingToken;
    private User user;

    ContractExecutionService contractExecutionService;

    public InGameContractConnector(User user) throws Exception
    {
        this.user = user;
        this.contractExecutionService = new ContractExecutionService();
        playerToadKingToken = ToadKingToken.load(ServerContractInitiator.ToadKingToken_contractAddress,
                web3j,
                Credentials.create(user.getUserWallet().getPrivateKey()),
                BigInteger.ZERO, BigInteger.valueOf(182865));
        System.out.println("User " + user.getName() + " has " + playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send());

    }

    public BigInteger GetBalance() throws Exception {
        return playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send();
    }

    public void ReturnAllMoney() throws Exception {
        playerToadKingToken.transfer(Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress(), GetBalance()).send();
    }

    //give 500 coin
    public void RequestMoney() throws Exception {
        contractExecutionService.TransferTokenFromCentral(user, 500);
    }

    public void RequestMoney(int amount) throws Exception {
        contractExecutionService.TransferTokenFromCentral(user, amount);
    }

}
