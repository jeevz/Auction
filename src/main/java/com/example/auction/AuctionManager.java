package com.example.auction;

import com.example.auction.model.Auction;
import com.example.auction.services.AuctionService;

public interface AuctionManager {

    AuctionService getAuctionService(String auctionId);

    void startAuction(String auctionId);

    void stopAuction(String auctionId);

    void scheduleAuctions();

}
