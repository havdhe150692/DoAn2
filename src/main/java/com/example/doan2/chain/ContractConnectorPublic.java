package com.example.doan2.chain;

import okhttp3.OkHttpClient;
import org.web3j.abi.FunctionEncoder;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.core.methods.response.Web3ClientVersion;
import org.web3j.protocol.http.HttpService;
import org.web3j.quorum.Node;
import org.web3j.quorum.Quorum;
import org.web3j.quorum.enclave.Enclave;
import org.web3j.quorum.enclave.Tessera;
import org.web3j.quorum.enclave.protocol.EnclaveService;
import org.web3j.quorum.tx.ClientTransactionManager;
import org.web3j.quorum.tx.QuorumTransactionManager;
import org.web3j.tx.gas.DefaultGasProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.function.Function;


public class ContractConnectorPublic {

    public ContractConnectorPublic() throws Exception {
        Start();
    }


    public void Start() throws Exception {
        Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:20000"));

        TKT tkt = TKT.load("0xb30e48fCE296D7787e4A6C338c943ceB0b15974B",
                web3j,
                Credentials.create("0xecb53dc998e8f1b5e3633a4ac9914595ba55ce0bb05acd632ce8d115de1e0aec"),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        if (tkt.isValid()) {
            System.out.println(" is valid");
            System.out.println(tkt.name().send());
            System.out.println(tkt.symbol().send());
            System.out.println(tkt.totalSupply().send());
            System.out.println(tkt.owner().send());
            System.out.println(tkt.balanceOf("0x5a7b17Ba110f5ec1BDeF1A814381552D0E74db91").send());
            System.out.println(tkt.balanceOf("0xDCd34f0ca992eA1Dfc0BD6F3dE117519E6dC1427").send());
            System.out.println(tkt.requestCurrentGasPrice());
        }
        else
        {
            System.out.println(" is not valid");
        }

        String pk = "0xecb53dc998e8f1b5e3633a4ac9914595ba55ce0bb05acd632ce8d115de1e0aec";
        Credentials credentials = Credentials.create(pk);

        String contractAddress = "0xb30e48fCE296D7787e4A6C338c943ceB0b15974B";

        TransactionReceipt transactionReceipt = tkt.transfer("0xDCd34f0ca992eA1Dfc0BD6F3dE117519E6dC1427", BigInteger.valueOf(50000)).send();
        System.out.println("Transaction Hash is" + transactionReceipt.getTransactionHash());


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
