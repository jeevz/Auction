package com.example.auction.bidding;

import com.example.auction.items.Item;
import com.example.auction.model.Bid;
import com.example.auction.pipe.Pipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BiddingItem {
    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingItem.class);

    public static enum State {
        LISTED,
        OPEN,
        CLOSE;
    }
    private String biddingItemId;
    private String auctionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private State state;
    private Item item;
    private Map<String, Bid> bids;
    private Pipe<Bid, Bid> bidPipe;
    private Disposable bidPipeDisposable;
    private Bid topBid;

    public BiddingItem(String biddingItemId, String auctionId,
                       LocalDateTime startTime, LocalDateTime endTime) {
        this.biddingItemId = biddingItemId;
        this.auctionId = auctionId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.bids = new ConcurrentHashMap<>();
        state = State.LISTED;
    }

    public String getBiddingItemId() {
        return biddingItemId;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public void setBiddingItemId(String biddingItemId) {
        this.biddingItemId = biddingItemId;
    }

    public void addBid(Bid bid) {
        bids.put(bid.getBidId(), bid);
    }

    public void setTopBid(Bid bid) {
        topBid = bid;
    }

    public Bid getTopBid() {
        return topBid;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void bid(Bid bid) {
        if (State.OPEN == state) {
            LOGGER.info("Publishing Bid {} on BiddingItem {}", bid.getBidId(), this.getBiddingItemId());
            bidPipe.getSink().ifPresent(sink -> sink.publish(bid));
        } else {
            LOGGER.warn("BiddingItem is not OPEN, Ignoring Bid {} on BiddingItem {}"
                    , bid.getBidId(), this.getBiddingItemId());
        }
    }

    public void publish(Bid bid) {

    }

    public void open() {
        bidPipe = Pipe.create(new BidProcessor(this));
        bidPipeDisposable = bidPipe.subscribe(this::publish);
        state = State.OPEN;
    }

    public void close() {
        bidPipeDisposable.dispose();
        state = State.CLOSE;
    }
}
