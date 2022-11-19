package com.example.doan2.chain;

import okhttp3.OkHttpClient;
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


public class ContractConnector {



    public ContractConnector() throws Exception {
        Start();
    }


    public void Start() throws Exception {
//        Web3j web3j = Web3j.build(new HttpService("http://172.16.239.16:8545/"));
//        String greeting;
//        ToadKingToken tkt = ToadKingToken.load("0x30f553cdfe0005bbbe9a91afbb2e6d611c6ab3c2", web3j, Credentials.create("0x8bbbb1b345af56b560a5b20bd4b0ed1cd8cc9958a16262bc75118453cb546df7"), new DefaultGasProvider());
//        if (tkt.isValid()) {
//            System.out.println(" is valid");
//        }
//        else
//        {
//            System.out.println(" is not valid");
//        }
//        web3j.shutdown();


//        Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:20000"));
//        ToadKingToken tkt = ToadKingToken.deploy(
//                web3j,
//                Credentials.create("8bbbb1b345af56b560a5b20bd4b0ed1cd8cc9958a16262bc75118453cb546df7"),
//                BigInteger.valueOf(0) , BigInteger.valueOf(0)).send();
//        if (tkt.isValid()) {
//           System.out.println(" is valid");
//        }
//        else
//        {
//            System.out.println(" is not valid");
//        }
//        web3j.shutdown();


//         Map<String,String> allNodeNamesToPublicKeysMap = new HashMap<String, String>(){{
//            put("node1","BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
//            put("node2","QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
//            put("node3","1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg=");
//        }};


//
//        Quorum quorum = Quorum.build(new HttpService("http://192.168.171.128:20000"));
//
//        Web3ClientVersion web3ClientVersion = quorum.web3ClientVersion().send();
//        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
//        System.out.println("clientVersion is" + clientVersion);
//
//        List<String> privateFor = allNodeNamesToPublicKeysMap;
//
//        EnclaveService enclaveService = new EnclaveService("http://192.168.171.128", 9081, new OkHttpClient());
//        Enclave enclave = new Tessera(enclaveService, quorum);
//        QuorumTransactionManager quorumTransactionManager = new QuorumTransactionManager(
//                quorum,
//                Credentials.create("8bbbb1b345af56b560a5b20bd4b0ed1cd8cc9958a16262bc75118453cb546df7"),
//                "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=",
//                privateFor, enclave);
//
//
//        ToadKingToken tkt = ToadKingToken.deploy(
//                quorum,
//                quorumTransactionManager,
//                new DefaultGasProvider());

        Quorum quorum = Quorum.build(new HttpService("http://192.168.171.128:20000"));
        Web3ClientVersion web3ClientVersion = quorum.web3ClientVersion().send();
        String clientVersion = web3ClientVersion.getWeb3ClientVersion();
        System.out.println("clientVersion is" + clientVersion);

//        ToadKingToken tkt = ToadKingToken.load(
//                "0x5739fd71cda45d86c7f3c81f28972c03ff380260",
//                quorum,
//                Credentials.create("8bbbb1b345af56b560a5b20bd4b0ed1cd8cc9958a16262bc75118453cb546df7"),
//                BigInteger.valueOf(0), BigInteger.valueOf(255000));

        List<String> allNodeNamesToPublicKeysMap = new ArrayList<String>();
        allNodeNamesToPublicKeysMap.add("BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=");
        allNodeNamesToPublicKeysMap.add("QfeDAys9MPDs2XHExtc84jKGHxZg/aj52DTh0vtA3Xc=");
        allNodeNamesToPublicKeysMap.add("1iTZde/ndBHvzhcl7V68x44Vx7pl8nwx9LqnM/AfJUg=");


        //Create ClientTransactionManager object by passing QuorumConnection parameters and privateFor - this will handle privacy requirements
        //Deploy the new thread contract. This returns a thread contract object
        //ToadKingToken tkt = ToadKingToken.deploy(quorum, clientTransactionManager, BigInteger.valueOf(0), BigInteger.valueOf(100000000)).send();

        //Extract contract address from thread contract object obtained in 2.d
        //String newThreadContractAddress = tkt.getContractAddress();
        //System.out.println(newThreadContractAddress);

        EnclaveService enclaveService = new EnclaveService("http://192.168.171.128", 9081, new OkHttpClient());
        Enclave enclave = new Tessera(enclaveService, quorum);

        // load the account from the filesystem
       // Credentials credentials =  Credentials.create("8bbbb1b345af56b560a5b20bd4b0ed1cd8cc9958a16262bc75118453cb546df7");
        Credentials credentials = WalletUtils.loadCredentials("", "src/main/java/com/example/doan2/chain/key");
        QuorumTransactionManager qrtxm = new QuorumTransactionManager(quorum,
                 credentials,
                "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=",
                allNodeNamesToPublicKeysMap, enclave);


        Node a = new Node("0x13a52aab892e1322e8b52506276363d4754c122e", allNodeNamesToPublicKeysMap,"http://192.168.171.128:20000");

//        QuorumTransactionManager qrtxm = new QuorumTransactionManager(quorum,
//                enclave,   credentials,1
//                "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=",
//                allNodeNamesToPublicKeysMap);



//        ClientTransactionManager clientTransactionManager = new ClientTransactionManager(quorum,
//                "0x13a52aab892e1322e8b52506276363d4754c122e",
//                "BULeR8JyUWhiuuCMU/HLA0Q5pzkYT+cHII3ZKBey3Bo=", allNodeNamesToPublicKeysMap);



        TKT toadKingToken = TKT.deploy(quorum,
                qrtxm,
               new DefaultGasProvider()).send();

        System.out.println("Contract address:" + toadKingToken.getContractAddress());
        System.out.println(toadKingToken.getTransactionReceipt());

//        Quorum quorum = Quorum.build(new HttpService("http://192.168.171.128:20000"));
//        Web3j web3j =Web3j.build(new HttpService("http://192.168.171.128:8545"));
//        Credentials credentials;
//        TKT tkt = TKT.load("0x9db181e9b4230dc1525496296e32fa914441d852",
//                quorum,
//                credentials = WalletUtils.loadCredentials("", "src/main/java/com/example/doan2/chain/key"),
//                new DefaultGasProvider());


//        if (tkt.isValid()) {
//            System.out.println(" is valid");
//        }
//        else
//        {
//            System.out.println( tkt.name());
//            System.out.println( tkt.getContractAddress());
//            System.out.println( tkt.getContractBinary());
//            System.out.println( tkt.getTransactionReceipt());
//            System.out.println( tkt.getDeployedAddress("1337"));
//            System.out.println(" is not valid");
//        }


    }



}
