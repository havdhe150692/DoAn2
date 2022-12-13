package com.example.doan2.chain;

import com.example.doan2.chain.smartcontract.ToadKingMarketplace;
import com.example.doan2.chain.smartcontract.ToadKingNFT;
import com.example.doan2.chain.smartcontract.ToadKingToken;
import com.example.doan2.entity.User;
import com.example.doan2.service.AdminContractExecutionService;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;

import java.math.BigInteger;

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


    public void ListNFT(int tokenId, int price) throws Exception {
        //var transaction1 = playerToadKingNFT.approve(Credentials.create(ServerContractInitiator.hostAccountCredential).getAddress(), BigInteger.valueOf(tokenId)).send();
        var transaction2 = playerToadKingMarket.listNft(BigInteger.valueOf(tokenId), BigInteger.valueOf(price)).send();
        System.out.println("Transaction 1 " + transaction2);


     //   System.out.println(transaction2);
    }

    public void GetMarketItem(int listingId) throws Exception {
        ToadKingMarketplace.ToadNFTMarket t = playerToadKingMarket.getMarketItem(BigInteger.valueOf(listingId)).send();
        System.out.println(t.toString());

    }

    public void GetNFTSellHistory(int tokenId) throws Exception {
        var t = playerToadKingMarket.getNftSellHistory(BigInteger.valueOf(tokenId)).send();
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
        System.out.println(t1.toString());
        return t1.toString();
    }

    public String GetMyListingNFT() throws Exception {
        var t1  = playerToadKingMarket.getMyListingNfts().send();
        System.out.println(t1.toString());
        return t1.toString();
    }

}
