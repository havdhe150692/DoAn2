pragma solidity ^0.8.13;

import "@openzeppelin/contracts/security/ReentrancyGuard.sol";
import "@openzeppelin/contracts/access/Ownable.sol";
import "@openzeppelin/contracts/utils/Counters.sol";
import "./ToadKingToken.sol";
import "./ToadKingNFT.sol";


contract ToadKingMarketplace is ReentrancyGuard, Ownable {

    ToadKingToken public toadKingCoin;
    ToadKingNFT public toadKingNFT;

    using Counters for Counters.Counter;
    Counters.Counter private _marketItemIds;
     Counters.Counter private _nftSold;

    uint256 platformFee = 1;
    uint256 deno = 100;

    mapping(uint256 => ToadNFTMarket) private toadMarketItems;

    constructor(address _toadKingTokenContract, address _toadKingNFTContract) {
        toadKingCoin = ToadKingToken(_toadKingTokenContract);
        toadKingNFT = ToadKingNFT(_toadKingNFTContract);

    }

    struct ToadNFTMarket {
        uint256 itemId;
        uint256 tokenId;
        uint256 price;
        address payable seller;
        address payable buyer;
        bool isSelling;
    }

    event ToadNFTListed(
        uint256 itemId,
        uint256 tokenId,
        address seller,
        address buyer,
        uint256 price
    );

    event ToadNFTSaleCanceled(
        uint256 itemId,
        uint256 tokenId,
        address seller,
        address buyer,
        uint256 price
    );

    event ToadNFTSold(
        uint256 itemId,
        uint256 tokenId,
        address seller,
        address buyer,
        uint256 price
    );

    event Logging(
        address a,
        address b,
        address c
    );

    modifier marketplaceItemExist(uint256 _marketplaceItemId) {
        require(_marketplaceItemId > 0, "MarketItemId should be > 0");
        require(
            _marketplaceItemId <= _marketItemIds.current(),
            "MarketItemId should be exist"
        );
        _;
    }

    modifier onlyTokenOwner(uint256 _tokenId) {
        require(
            toadKingNFT.ownerOf(_tokenId) == msg.sender,
            "You are not NFT's owner"
        );
        _;

    }



    modifier validPrice(uint256 _price) {
        require(_price > 0, "Price must > 0");
        _;
    }

    function test() public
    {
        emit Logging(msg.sender, owner(), address(this));
    }

    function listNft(uint256 _tokenId, uint256 _price) onlyTokenOwner(_tokenId) validPrice(_price) public {

        _marketItemIds.increment();

        uint256 marketItemId = _marketItemIds.current();

        toadMarketItems[marketItemId] = ToadNFTMarket(
            marketItemId,
            _tokenId,
            _price,
            payable(msg.sender),
            payable(address(0)),
            true
        );

        //Approve NFT first before this
        toadKingNFT.transferFrom(msg.sender, address(this), _tokenId);


        emit ToadNFTListed(
            marketItemId,
            _tokenId,
            msg.sender,
            address(this),
            _price
        );
    }

    function cancelSellNft(uint256 _marketItemId)
        public
        marketplaceItemExist(_marketItemId) {

        ToadNFTMarket storage toadNFTMarketItem = toadMarketItems[_marketItemId];
        toadNFTMarketItem.isSelling = false;

        // no need to Approve

        toadKingNFT.transferFrom(
            address(this),
            msg.sender,
            toadNFTMarketItem.tokenId
        );

        emit ToadNFTSaleCanceled(
            toadNFTMarketItem.itemId,
            toadNFTMarketItem.tokenId,
            toadNFTMarketItem.seller,
            toadNFTMarketItem.buyer,
            toadNFTMarketItem.price
        );
    }

    function buyNft(uint256 _marketItemId)
        public
        payable
        marketplaceItemExist(_marketItemId) {

        ToadNFTMarket storage toadNFTMarket = toadMarketItems[_marketItemId];
        require(toadNFTMarket.isSelling == true, "NFT is not Selling");
        require(
            toadNFTMarket.seller != msg.sender,
            "You can not buy your own NFT"
        );

        uint256 price = toadNFTMarket.price;
        uint256 marketFee = (price * platformFee) / deno;

        uint256 senderBalance = toadKingCoin.balanceOf(msg.sender);
        require(
            senderBalance >= price,
            "You do not have enough TKT to buy this NFT"
        );

        uint256 allowance = toadKingCoin.allowance(
            msg.sender, address(this)
        );
        require(
            allowance >= price,
            "You do not approve enough TKT to buy this NFT"
        );


        // NEED APPROVE FIRST
        toadKingCoin.transferFrom(msg.sender, address(this), price);

        toadKingCoin.transfer(toadNFTMarket.seller, price - marketFee);

        toadKingCoin.transfer(owner(), marketFee);

        toadNFTMarket.buyer = payable(msg.sender);

        _nftSold.increment();

        //transfer nft
        toadKingNFT.transferFrom(address(this), msg.sender, toadNFTMarket.tokenId);

        toadNFTMarket.isSelling = false;
        toadNFTMarket.buyer = payable(msg.sender);

        emit ToadNFTSold(
            toadNFTMarket.itemId,
            toadNFTMarket.tokenId,
            toadNFTMarket.seller,
            toadNFTMarket.buyer,
            price
        );
    }


    function getMarketItem(uint256 _marketItemId)
        public
        view
        marketplaceItemExist(_marketItemId)
        returns (ToadNFTMarket memory) {
        return toadMarketItems[_marketItemId];
    }

    function getListingNfts() public view returns (ToadNFTMarket[] memory) {
        uint256 nftCount = _marketItemIds.current();
        uint256 unsoldNftsCount = nftCount - _nftSold.current();

        ToadNFTMarket[] memory nfts = new ToadNFTMarket[](unsoldNftsCount);
        uint256 nftsIndex = 0;
        for (uint256 i = 0; i < nftCount; i++) {
            if (toadMarketItems[i + 1].isSelling) {
                nfts[nftsIndex] = toadMarketItems[i + 1];
                nftsIndex++;
            }
        }
        return nfts;
    }

    function getMyListingNfts() public view returns (ToadNFTMarket[] memory) {
        uint256 nftCount = _marketItemIds.current();
        uint256 myListingNftCount = 0;
        for (uint256 i = 0; i < nftCount; i++) {
            if (
                toadMarketItems[i + 1].seller == msg.sender &&
                toadMarketItems[i + 1].isSelling
            ) {
                myListingNftCount++;
            }
        }

        ToadNFTMarket[] memory nfts = new ToadNFTMarket[](myListingNftCount);
        uint256 nftsIndex = 0;
        for (uint256 i = 0; i < nftCount; i++) {
            if (
                toadMarketItems[i + 1].seller == msg.sender &&
                toadMarketItems[i + 1].isSelling
            ) {
                nfts[nftsIndex] = toadMarketItems[i + 1];
                nftsIndex++;
            }
        }
        return nfts;
    }

    function getNftSellHistory(uint256 _tokenId)
        public
        view
        returns (ToadNFTMarket[] memory) {

        uint256 nftCount = _marketItemIds.current();
        uint256 nftSellHistoryCount = 0;
        for (uint256 i = 0; i < nftCount; i++) {
            if (
                toadMarketItems[i + 1].tokenId == _tokenId &&
                !toadMarketItems[i + 1].isSelling
            ) {
                nftSellHistoryCount++;
            }
        }

        ToadNFTMarket[] memory nfts = new ToadNFTMarket[](nftSellHistoryCount);
        uint256 nftsIndex = 0;
        for (uint256 i = 0; i < nftCount; i++) {
            if (
                toadMarketItems[i + 1].tokenId == _tokenId &&
                !toadMarketItems[i + 1].isSelling
            ) {
                nfts[nftsIndex] = toadMarketItems[i + 1];
                nftsIndex++;
            }
        }
        return nfts;
    }

}