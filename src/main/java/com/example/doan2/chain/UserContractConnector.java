package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingMarketplace;
import com.example.doan2.chain.smartcontract.ToadKingNFT;
import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.User;
import com.example.doan2.service.AdminContractExecutionService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.EthLog;

import java.math.BigInteger;
import java.util.List;

public class UserContractConnector {

    private Web3j web3j = ServerContractInitiator.web3j;

    public ToadKingToken playerToadKingToken;
    public ToadKingNFT playerToadKingNFT;

    public ToadKingMarketplace playerToadKingMarket;
    private User user;

    AdminContractExecutionService adminContractExecutionService;

    public UserContractConnector(User user) throws Exception
    {
        this.user = user;
        this.adminContractExecutionService = new AdminContractExecutionService();
        playerToadKingToken = ToadKingToken.load(ServerContractInitiator.ToadKingToken_contractAddress,
                web3j,
                Credentials.create(user.getUserWallet().getPrivateKey()),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        playerToadKingNFT = ToadKingNFT.load(ServerContractInitiator.ToadKingNFT_contractAddress,
                web3j,
                Credentials.create(user.getUserWallet().getPrivateKey()),
                BigInteger.ZERO, BigInteger.valueOf(182865));

        playerToadKingMarket = ToadKingMarketplace.load(ServerContractInitiator.ToadKingMarketplace_contractAddress,
                web3j,
                Credentials.create(user.getUserWallet().getPrivateKey()),
                BigInteger.ZERO, BigInteger.valueOf(16234336));

        System.out.println("User " + user.getName() + " has " + playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send() + " TKT");
        System.out.println("User " + user.getName() + " has " + playerToadKingNFT.balanceOf(user.getUserWallet().getAddress()).send() + " TKNFT");
        System.out.println("Contract Marketplace is valid = "  + playerToadKingMarket.isValid() + " " + playerToadKingMarket.getListingNfts().send());
        System.out.println(playerToadKingNFT.setApprovalForAll(ServerContractInitiator.ToadKingMarketplace_contractAddress,  true).send().getTransactionHash());


    }

    private List<EthLog.LogResult> createFilterForEvent(String encodedEventSignature)
            throws Exception {
        EthFilter ethFilter =
                new EthFilter(
                        DefaultBlockParameterName.EARLIEST,
                        DefaultBlockParameterName.LATEST,
                        ServerContractInitiator.ToadKingMarketplace_contractAddress);

        ethFilter.addSingleTopic(encodedEventSignature);
        EthLog ethLog = web3j.ethGetLogs(ethFilter).send();
        return ethLog.getLogs();
    }


    public BigInteger GetBalance() throws Exception {
        return playerToadKingToken.balanceOf(user.getUserWallet().getAddress()).send();
    }

    public void ReturnAllMoney() throws Exception {
        playerToadKingToken.transfer(Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress(), GetBalance()).send();
    }

    //give 500 coin
    public void RequestMoney() throws Exception {
        adminContractExecutionService.TransferTokenFromCentral(user, 500);
    }

    public void RequestMoney(int amount) throws Exception {
        adminContractExecutionService.TransferTokenFromCentral(user, amount);
    }


    public ToadKingMarketplace.ToadNFTListedEventResponse ListNFT(int tokenId, int price) throws Exception {
        //var transaction1 = playerToadKingNFT.approve(Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress(), BigInteger.valueOf(tokenId)).send();
        var transaction2 = playerToadKingMarket.listNft(BigInteger.valueOf(tokenId), BigInteger.valueOf(price)).send();
        var event = ToadKingMarketplace.getToadNFTListedEvents(transaction2);

        return event.get(0);

    }

    public void GetMarketItem(int listingId) throws Exception {
        ToadKingMarketplace.ToadNFTMarket t = playerToadKingMarket.getMarketItem(BigInteger.valueOf(listingId)).send();
        System.out.println(t.toString());

    }

    public String GetNFTSellHistory(int tokenId) throws Exception {
        var t = playerToadKingMarket.getNftSellHistory(BigInteger.valueOf(tokenId)).send();
        return t.toString();
    }

    public void BuyNFT(int listingId, int price) throws Exception {
        playerToadKingToken.approve(ServerContractInitiator.ToadKingMarketplace_contractAddress, BigInteger.valueOf(price)).send();
        var t = playerToadKingMarket.buyNft(BigInteger.valueOf(listingId), BigInteger.ZERO).send();
        System.out.println(t.getTransactionHash());
    }

    public void CancelSellNFT(int listingId) throws Exception {
        var t = playerToadKingMarket.cancelSellNft(BigInteger.valueOf(listingId)).send();
        System.out.println(t.getTransactionHash());
    }

    public String GetListingNFT() throws Exception {
        var t1 = playerToadKingMarket.getListingNfts().send();
        for (int i = 0; i < t1.size(); i++) {
            Object o =  t1.get(i);
            ToadKingMarketplace.ToadNFTMarket obj = (ToadKingMarketplace.ToadNFTMarket) o;
            System.out.println(obj.toString());
        }

        return t1.toString();
    }

    public List<ToadKingMarketplace.ToadNFTMarket> GetMyListingNFT() throws Exception {
        var t1  = playerToadKingMarket.getMyListingNfts().send();
        System.out.println(t1.toString());
        return t1;
    }

}
