package com.example.auction.data;

import com.example.auction.bidding.BiddingItem;
import com.example.auction.model.Auction;

import java.util.Map;
import java.util.Optional;

public class IgniteDataSource implements DataSource {
    @Override
    public Map<String, Auction> getAuctions() {
        return null;
    }

    @Override
    public void addAuction(Auction auction) {

    }

    @Override
    public void updateAuction(Auction auction) {

    }

    @Override
    public Optional<Auction> getAuction(String auctionId) {
        return Optional.empty();
    }

    @Override
    public Optional<Map<String, BiddingItem>> getBiddingItems(String auctionId) {
        return Optional.empty();
    }

    @Override
    public void addBiddingItem(BiddingItem biddingItem) {

    }

    @Override
    public void updateBiddingItem(BiddingItem biddingItem) {

    }
}
