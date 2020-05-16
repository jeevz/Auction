package com.example.auction.bidding;

import com.example.auction.utils.TimeUtils;
import com.example.auction.model.Bid;
import com.example.auction.pipe.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BidProcessor implements Handler<Bid, Bid> {
    Logger LOGGER = LoggerFactory.getLogger(BidProcessor.class);

    BiddingItem biddingItem;

    public BidProcessor(BiddingItem biddingItem) {
        this.biddingItem = biddingItem;
    }

    @Override
    public Bid handle(Bid bid) {
        if (biddingItem.getTopBid() == null
                || biddingItem.getTopBid().getPrice() < bid.getPrice()) {
            biddingItem.setTopBid(bid);
            bid.setState(Bid.State.SUCCESS);
        } else {
            bid.setState(Bid.State.FAILED);
        }
        bid.setProcessedTime(TimeUtils.utcNow());
        biddingItem.addBid(bid);
        LOGGER.info("Processed {}", bid);
        return bid;
    }
}
