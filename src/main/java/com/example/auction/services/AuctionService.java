package com.example.auction.services;

import com.example.auction.events.AuctionEvent;
import com.example.auction.events.BiddingEvent;
import com.example.auction.bidding.BiddingItem;
import com.example.auction.model.Auction;
import com.example.auction.model.Bid;
import com.example.auction.users.User;

public interface AuctionService {

    Auction getAuction();

    void setRegisteredUsers();

    void addRegisteredUser(User user);

    void addBiddingItem(BiddingItem item);

    void setBiddingItems();

    void scheduleItems();

    void scheduleItem(BiddingItem biddingItem);

    void handleEvent(AuctionEvent event);

    void handleEvent(BiddingEvent event);

    void publish(Bid bid);

    void start();

    void stop();

    void suspend();

    void pause();
}
