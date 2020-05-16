package com.example.auction.events;

import com.example.auction.services.AuctionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuctionEvent {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuctionEvent.class);

    public static enum Action {
        START,
        STOP,
        PAUSE,
        SUSPEND;
    }

    private final String auctionId;
    private final Action action;

    public AuctionEvent(String auctionId, Action action) {
        this.auctionId = auctionId;
        this.action = action;
    }

    public String getAuctionId() {
        return auctionId;
    }

    public Action getAction() {
        return action;
    }

    public void onEvent(AuctionService auction) {
        switch (this.action) {
            case START: {
                LOGGER.info("Starting Auction {}", getAuctionId());
                auction.start();
                break;
            }
            case STOP: {
                LOGGER.info("Closing Auction {}", getAuctionId());
                auction.stop();
                break;
            }
            case PAUSE: {
                LOGGER.info("Pausing Auction {}", getAuctionId());
                auction.pause();
                break;
            }
            case SUSPEND: {
                LOGGER.info("Suspending Auction {}", getAuctionId());
                auction.suspend();
                break;
            }
            default: {
                throw new UnsupportedOperationException(this.action + " on Auction " + getAuctionId());
            }
        }
    }
}
