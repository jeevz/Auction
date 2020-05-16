package com.example.auction.data;

import com.example.auction.bidding.BiddingItem;
import com.example.auction.model.Auction;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class MemoryDataSource implements DataSource {

    private Map<String, Auction> auctions;
    private Map<String, Map<String, BiddingItem>> biddingItems;

    public MemoryDataSource() {
        auctions = new ConcurrentHashMap<>();
        biddingItems = new ConcurrentHashMap<>();
    }

    @Override
    public Map<String, Auction> getAuctions() {
        return auctions;
    }

    @Override
    public void addAuction(Auction auction) {
        auctions.put(auction.getAuctionId(), auction);
    }

    @Override
    public void updateAuction(Auction auction) {
        auctions.put(auction.getAuctionId(), auction);
    }

    @Override
    public Optional<Auction> getAuction(String auctionId) {
        return Optional.ofNullable(auctions.get(auctionId));
    }

    @Override
    public void addBiddingItem(BiddingItem biddingItem) {
        Map<String, BiddingItem> items = biddingItems.computeIfAbsent(biddingItem.getAuctionId(),
                key -> new ConcurrentHashMap<String, BiddingItem>());
        items.put(biddingItem.getBiddingItemId(), biddingItem);
    }

    @Override
    public void updateBiddingItem(BiddingItem biddingItem) {
        Map<String, BiddingItem> items = biddingItems.computeIfAbsent(biddingItem.getAuctionId(),
                key -> new ConcurrentHashMap<String, BiddingItem>());
        items.put(biddingItem.getBiddingItemId(), biddingItem);
    }

    @Override
    public Optional<Map<String, BiddingItem>> getBiddingItems(String auctionId) {
        return Optional.ofNullable(biddingItems.get(auctionId));
    }
}
