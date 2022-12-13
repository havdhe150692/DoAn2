package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingToken;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;

import java.math.BigInteger;


public class DemoPublicContract {

    public DemoPublicContract() throws Exception {
        Start();
    }


    public void Start() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:8545"));

        ToadKingToken toadKingToken = ToadKingToken.load("0xb30e48fCE296D7787e4A6C338c943ceB0b15974B",
                web3j,
                Credentials.create("0xecb53dc998e8f1b5e3633a4ac9914595ba55ce0bb05acd632ce8d115de1e0aec"),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        if (toadKingToken.isValid()) {
            System.out.println(" is valid");
            System.out.println(toadKingToken.name().send());
            System.out.println(toadKingToken.symbol().send());
            System.out.println(toadKingToken.totalSupply().send());
            //System.out.println(toadKingToken.owner().send());
            System.out.println(toadKingToken.balanceOf("0x5a7b17Ba110f5ec1BDeF1A814381552D0E74db91").send());
            System.out.println(toadKingToken.balanceOf("0x029834be5bfc967fc5134ba53df0e5b8666b9c10").send());
            System.out.println(toadKingToken.requestCurrentGasPrice());
        }
        else
        {
            System.out.println(" is not valid");
        }


        TransactionReceipt transactionReceipt = toadKingToken.transfer("0x029834be5bfc967fc5134ba53df0e5b8666b9c10", BigInteger.valueOf(50000)).send();
        System.out.println("Transaction Hash is" + transactionReceipt.getTransactionHash());
        System.out.println(toadKingToken.balanceOf("0x5a7b17Ba110f5ec1BDeF1A814381552D0E74db91").send());
        System.out.println(toadKingToken.balanceOf("0x029834be5bfc967fc5134ba53df0e5b8666b9c10").send());


//        Function function = new Function("set", // Function name
//                Arrays.asList(new Uint(BigInteger.valueOf(20))), // Function input parameters
//                Collections.emptyList()); // Function returned parameters
//
////Encode function values in transaction data format
//        String txData = FunctionEncoder.encode(function);
//
//// RawTransactionManager use a wallet (credential) to create and sign transaction
//        TransactionManager txManager = new RawTransactionManager(web3j, credentials);
//
//// Send transaction
//        String txHash = txManager.sendTransaction(
//                DefaultGasProvider.GAS_PRICE,
//                DefaultGasProvider.GAS_LIMIT,
//                contractAddress,
//                txData,
//                BigInteger.ZERO).getTransactionHash();
//
//// Wait for transaction to be mined
//        TransactionReceiptProcessor receiptProcessor = new PollingTransactionReceiptProcessor(
//                web3j,
//                TransactionManager.DEFAULT_POLLING_FREQUENCY,
//                TransactionManager.DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH);
//        TransactionReceipt txReceipt = receiptProcessor.waitForTransactionReceipt(txHash);




    }

}
