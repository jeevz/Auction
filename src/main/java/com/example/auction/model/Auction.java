package com.example.auction.model;

import java.time.LocalDateTime;

public class Auction {
    public static enum State {
        CREATED,
        OPENED,
        CLOSED,
        SUSPENDED,
        PAUSED,
    }
    private final String auctionId;
    private final String auctionName;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;
    private State state;

    public Auction(String auctionId, String auctionName, LocalDateTime startTime, LocalDateTime endTime) {
        this.auctionId = auctionId;
        this.auctionName = auctionName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.state = State.CREATED;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public String getAuctionName() {
        return auctionName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Auction{" +
                "auctionId='" + auctionId + '\'' +
                ", auctionName='" + auctionName + '\'' +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", state=" + state +
                '}';
    }

}
