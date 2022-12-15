package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingMarketplace;
import com.example.doan2.chain.smartcontract.ToadKingNFT;
import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.ToadIngame;
import com.example.doan2.entity.User;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.FunctionReturnDecoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.Web3jService;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.http.HttpService;
import org.web3j.spring.autoconfigure.Web3jAutoConfiguration;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

@Service
@Scope("singleton")
public class ServerContractInitiator {

    public static String hostAccountCredential = "ad8e4a8d67b9856c0bf95dfa76b4253f4276339f628c24cdf6cd14dec951ff97";
    //public static Web3j web3j = Web3j.build(new HttpService("http://192.168.171.128:8545"));


   public static Web3j web3j = Web3j.build(new HttpService("http://4.194.80.55:8545",
                                            new OkHttpClient.Builder().connectTimeout(60*60*3, TimeUnit.SECONDS)
                                                                     .writeTimeout(60*60*3, TimeUnit.SECONDS)
                                                                     .readTimeout(60*60*3, TimeUnit.SECONDS).build()));

    public static String ToadKingToken_contractAddress = "0x8533f27a9a53440feaf02edd2cb677502150f6d1";
    public static String ToadKingNFT_contractAddress = "0x21903bf4d0633df2b9e2a03f4a5b55c11b2b7c0f";
    public static String ToadKingMarketplace_contractAddress = "0xcc538236d36c2e29012c4b00b2357010084226bf";
    public static String hostAccountAddress = "0xb4a80296950b343A1A9a47c5d73fA5AeDb5B4273";

    public static  ToadKingMarketplace adminToadKingMarketplace;
    public static ToadKingToken adminToadKingToken;
    public static ToadKingNFT adminToadKingNFT;

    public ServerContractInitiator() throws Exception {
      try {
           System.out.println("Host Account Address is " + Credentials.create(hostAccountCredential).getAddress());
           DeployContract();
      }

      catch (Exception e)
      {
          System.out.println(e.getMessage());
          System.out.println("Blockchain not connected. Catched exception!");
      }


    }

    public void DeployContract() throws Exception {
        DeployToadKingToken();
        DeployToadKingNFT();
        DeployToadKingMarketplace();

        //SubscribeToEvent();
    }

    public static void TransferTokenFromCentral(User toUser, int amount) throws Exception {
        String address = toUser.getUserWallet().getAddress();
        System.out.println(adminToadKingToken.transfer(address, BigInteger.valueOf(amount))
                .send().getTransactionHash());

    }


    public void SubscribeToEvent() throws Exception
    {
        EthFilter filter1 = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, ToadKingToken_contractAddress);
        EthFilter filter2 = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, ToadKingNFT_contractAddress);
        EthFilter filter3 = new EthFilter(DefaultBlockParameterName.LATEST, DefaultBlockParameterName.LATEST, ToadKingMarketplace_contractAddress);

        web3j.ethLogFlowable(filter1).subscribe(event -> {

            String eventHash = event.getTopics().get(0); // Index 0 is the event definition hash

            if(eventHash.equals(EventEncoder.encode(ToadKingToken.TRANSFER_EVENT))) { // Only MyEvent. You can also use filter.addSingleTopic(MY_EVENT_HASH)
                Address arg1 = (Address) FunctionReturnDecoder.decodeIndexedValue(event.getTopics().get(1), new TypeReference<Address>() {});
                Address arg2 = (Address) FunctionReturnDecoder.decodeIndexedValue(event.getTopics().get(2), new TypeReference<Address>() {});
                Uint256 arg3 = (Uint256) FunctionReturnDecoder.decodeIndexedValue(event.getData(), new TypeReference<Uint256>() {});

                System.out.print("Event received: = ");
                System.out.println(" -- " +arg1.toString());
                System.out.println(" -- " +arg2.toString());
                System.out.println(" -- " +arg3.toString());
            }
            else {
                System.out.println("Event received");
                System.out.println(event);
            }

        }, error -> {
            System.out.println("Error: " + error);
        });

        web3j.ethLogFlowable(filter2).subscribe(event -> {
            System.out.println("Event received");
            System.out.println(event);
        }, error -> {
            System.out.println("Error: " + error);
        });

        web3j.ethLogFlowable(filter3).subscribe(event -> {
            System.out.println("Event received");
            System.out.println(event);
        }, error -> {
            System.out.println("Error: " + error);
        });
    }



    public void DeployToadKingToken() throws Exception {
        ToadKingToken toadKingToken = ToadKingToken.load(ToadKingToken_contractAddress,
                web3j,
                Credentials.create(hostAccountCredential),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        if(!toadKingToken.isValid()) {
            System.out.println("Contract is not existed. Starting deployment....");
            toadKingToken = ToadKingToken.deploy(web3j,
                    Credentials.create(hostAccountCredential),
                    BigInteger.ZERO, BigInteger.valueOf(16234336)).send();

        }
        ToadKingToken_contractAddress = toadKingToken.getContractAddress();
        System.out.println("Token Contract at: " + toadKingToken.getContractAddress());
        adminToadKingToken = toadKingToken;
        System.out.println("Test TokenContract GetName " + adminToadKingToken.name().send());
    }

    public void DeployToadKingNFT() throws Exception {
        ToadKingNFT toadKingNFT = ToadKingNFT.load(ToadKingNFT_contractAddress,
                web3j,
                Credentials.create(hostAccountCredential),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        if(!toadKingNFT.isValid()) {
            System.out.println("Contract is not existed. Starting deployment....");
            toadKingNFT = ToadKingNFT.deploy(web3j,
                    Credentials.create(hostAccountCredential),
                    BigInteger.ZERO, BigInteger.valueOf(16234336)).send();

        }
        ToadKingNFT_contractAddress = toadKingNFT.getContractAddress();
        System.out.println("NFT Contract at: " + toadKingNFT.getContractAddress());
        adminToadKingNFT = toadKingNFT;
        System.out.println("Test NFTContract GetName " + adminToadKingNFT.name().send());
    }

    public void DeployToadKingMarketplace() throws Exception {

        ToadKingMarketplace toadKingMarketplace = ToadKingMarketplace.load(ToadKingMarketplace_contractAddress,
                web3j,
                Credentials.create(hostAccountCredential),
                BigInteger.ZERO, BigInteger.valueOf(16234336));

        if(!toadKingMarketplace.isValid())  {
            System.out.println("Contract is not existed. Starting deployment....");

            toadKingMarketplace = ToadKingMarketplace.deploy(web3j,
                    Credentials.create(hostAccountCredential),
                    BigInteger.ZERO, BigInteger.valueOf(16234336),
                    ToadKingToken_contractAddress, ToadKingNFT_contractAddress).send();
        }

        ToadKingMarketplace_contractAddress = toadKingMarketplace.getContractAddress();
        System.out.println("Marketplace Contract at: " + toadKingMarketplace.getContractAddress());
        adminToadKingMarketplace = toadKingMarketplace;

    }
}
