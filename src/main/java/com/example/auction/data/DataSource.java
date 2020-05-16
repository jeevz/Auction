package com.example.auction.data;

import com.example.auction.bidding.BiddingItem;
import com.example.auction.model.Auction;
import com.example.auction.users.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface DataSource {

    Map<String, Auction> getAuctions();

    void addAuction(Auction auction);

    void updateAuction(Auction auction);

    Optional<Auction> getAuction(String auctionId);

    Optional<Map<String, BiddingItem>> getBiddingItems(String auctionId);

    void addBiddingItem(BiddingItem biddingItem);

    void updateBiddingItem(BiddingItem biddingItem);

    Optional<List<User>> getRegisteredUsers(String auctionId);
}
