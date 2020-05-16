package com.example.auction.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Bid {

    public static enum State {
        OPEN,
        SUCCESS,
        FAILED,
        WON;
    }
    //TODO: Add Bid Type to handle for Dutch Auctions
    public static enum Type {
        ASK,
        BID;
    }
    private String bidId;
    private Double price;
    private String biddingItemId;
    private String userId;
    private LocalDateTime receivedTime;
    private LocalDateTime processedTime;
    private State state;

    public Bid(Double price, String biddingItemId, String userId) {
        this.bidId = UUID.randomUUID().toString();
        this.price = price;
        this.biddingItemId = biddingItemId;
        this.userId = userId;
        this.state = State.OPEN;
    }

    public String getBidId() {
        return bidId;
    }

    public void setBidId(String bidId) {
        this.bidId = bidId;
    }

    public String getBiddingItemId() {
        return biddingItemId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getUserId() {
        return userId;
    }

    public void setUser(String userId) {
        this.userId = userId;
    }

    public LocalDateTime getReceivedTime() {
        return receivedTime;
    }

    public void setReceivedTime(LocalDateTime receivedTime) {
        this.receivedTime = receivedTime;
    }

    public LocalDateTime getProcessedTime() {
        return processedTime;
    }

    public void setProcessedTime(LocalDateTime processedTime) {
        this.processedTime = processedTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Bid{" +
                "bidId='" + bidId + '\'' +
                ", price=" + price +
                ", biddingItemId=" + biddingItemId +
                ", userId=" + userId +
                ", receivedTime=" + receivedTime +
                ", processedTime=" + processedTime +
                ", state=" + state +
                '}';
    }

}
