package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;
@Service
@Scope("singleton")
public class ServerContractInitiator {

    private String hostAccountCredential = "0xecb53dc998e8f1b5e3633a4ac9914595ba55ce0bb05acd632ce8d115de1e0aec";
    private Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:8545"));

    public static String ToadKingToken_contractAddress = "0xb30e48fce296d7787e4a6c338c943ceb0b15974b";
    public static ToadKingToken adminToadKingToken;

    public ServerContractInitiator() throws Exception {
        try {
            DeployContract();
        }
        catch (Exception e)
        {
            System.out.println("OK I understand there is an exception");
        }


    }

    public void DeployContract() throws Exception {
        DeployToadKingToken();

    }

    public static void TransferTokenFromCentral(User toUser, int amount) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        System.out.println(adminToadKingToken.transfer(address, BigInteger.valueOf(amount))
                .send().getTransactionHash());

    }

    public void DeployToadKingToken() throws Exception {
        ToadKingToken toadKingToken = ToadKingToken.load(ToadKingToken_contractAddress,
                web3j,
                Credentials.create(hostAccountCredential),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        if(!toadKingToken.isValid()) {
            System.out.println("Contact is not existed. Starting deployment....");
            toadKingToken = ToadKingToken.deploy(web3j,
                    Credentials.create(hostAccountCredential),
                    BigInteger.ZERO, BigInteger.valueOf(16234336)).send();

        }
        ToadKingToken_contractAddress = toadKingToken.getContractAddress();
        System.out.println(toadKingToken.getContractAddress());
        adminToadKingToken = toadKingToken;
    }


}
