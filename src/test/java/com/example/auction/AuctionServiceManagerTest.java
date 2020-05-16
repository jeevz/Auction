package com.example.auction;

import com.example.auction.services.AuctionService;
import com.example.auction.utils.TimeUtils;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class AuctionServiceManagerTest {

    @Test
    void test_createAuction_when_auction_created_auction_state_should_be_CREATED() {
        AuctionManager auctionManager = new AuctionManager();
        String auctionId = UUID.randomUUID().toString();
        auctionManager.createAuction(auctionId,
                "TestAuction",
                TimeUtils.utcNow().plusMinutes(1),
                TimeUtils.utcNow().plusMinutes(5));
        assertEquals(AuctionService.State.CREATED, auctionManager.getAuction(auctionId).getAuctionState(), "Auction State should be CREATED");
    }

    @Test
    void test_startAuction_when_auction_started_auction_state_should_be_OPENED() throws InterruptedException {
        AuctionManager auctionManager = new AuctionManager();
        String auctionId = UUID.randomUUID().toString();
        auctionManager.createAuction(auctionId,
                "TestAuction",
                TimeUtils.utcNow().plusSeconds(5),
                TimeUtils.utcNow().plusSeconds(10));
        auctionManager.startAuction(auctionId);
        auctionManager.stopAuction(auctionId);
        //TODO: Find a better way to test this.
        Thread.sleep(5000);
        assertEquals(AuctionService.State.OPENED, auctionManager.getAuction(auctionId).getAuctionState(), "Auction State should be OPENED");
    }

}