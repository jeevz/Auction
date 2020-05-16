package com.example.auction.events;

import com.example.auction.model.Bid;
import com.example.auction.bidding.BiddingItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BiddingEvent {
    private static final Logger LOGGER = LoggerFactory.getLogger(BiddingEvent.class);

    public static enum Action {
        START,
        STOP,
        BID;
    }
    private String biddingItemId;
    private Action action;
    private Bid bid;

    public BiddingEvent(String biddingItemId, Action action) {
        this.biddingItemId = biddingItemId;
        this.action = action;
    }

    public BiddingEvent(String biddingItemId, Action action, Bid bid) {
        this(biddingItemId, action);
        this.bid = bid;
    }

    public String getBiddingItemId() {
        return biddingItemId;
    }

    public void onEvent(BiddingItem biddingItem) {
        switch (this.action) {
            case START: {
                LOGGER.info("Starting Bidding for Item {}", biddingItem.getBiddingItemId());
                biddingItem.open();
                break;
            }
            case STOP: {
                LOGGER.info("Stopping Bidding for Item {}", biddingItem.getBiddingItemId());
                biddingItem.close();
                break;
            }
            case BID:
                LOGGER.info("Processing Bid {} for Item {}", bid.getBidId(), biddingItem.getBiddingItemId());
                biddingItem.bid(bid);
                break;
        }
    }
}
