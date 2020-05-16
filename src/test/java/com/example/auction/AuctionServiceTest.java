package com.example.auction;

import com.example.auction.model.Bid;
import com.example.auction.bidding.BiddingItem;
import com.example.auction.services.AuctionService;
import com.example.auction.users.User;
import com.example.auction.utils.TimeUtils;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuctionServiceTest {

    int NO_BIDDING_ITEMS = 20;
    int NO_USERS = 10;
    int NO_BIDS = 100;
    int AUCTION_TIME_SECONDS = 5 * 60;

    List<BiddingItem> getBiddingItems() {
        List<BiddingItem> items = new ArrayList();
//        Random random = new Random();
        for (int i=0; i<NO_BIDDING_ITEMS; i++) {
//            int seconds = random.nextInt();
            BiddingItem item = new BiddingItem(
                    TimeUtils.utcNow().plusSeconds(0),
                    TimeUtils.utcNow().plusSeconds(AUCTION_TIME_SECONDS));
            item.setBiddingItemId(UUID.randomUUID().toString());
            items.add(item);
        }
        return items;
    }

    List<User> getUsers() {
        List<User> users = new ArrayList<>();
        for (int i=0; i < 20; i++) {
            users.add(new User("" + 1, "user" + 1, User.Role.BIDDER));
        }
        return users;
    }

    Map<BiddingItem, List<Bid>> getBids(List<BiddingItem> biddingItems) {
        Random random = new Random();
        Map<BiddingItem, List<Bid>> itemBids = new HashMap<>();
        for (int i=0; i<NO_BIDS; i++) {
            BiddingItem item = biddingItems.get(random.nextInt(10));
            Bid bid = new Bid(random.nextInt(100) * 1.5,
                    item.getBiddingItemId(),
                    getUsers().get(random.nextInt(NO_USERS)).getUserId());
            itemBids.computeIfAbsent(item, k -> new ArrayList<Bid>());
            itemBids.get(item).add(bid);
        }
        return itemBids;
    }

    @Test
    void test_scheduleItems_should_schedule_bidding_items_to_open_for_bidding() throws InterruptedException {
        String auctionId = UUID.randomUUID().toString();
        AuctionService auction = new AuctionService(auctionId,"TestAuction",
                TimeUtils.utcNow().plusSeconds(0),
                TimeUtils.utcNow().plusSeconds(AUCTION_TIME_SECONDS));

        List<BiddingItem> biddingItems = getBiddingItems();
        biddingItems.forEach(item -> auction.addBiddingItem(item));
        Map<BiddingItem, List<Bid>> unprocessedBids = getBids(biddingItems);

        auction.start();

        Thread.sleep(3000);
        unprocessedBids.forEach((biddingItem, bids) -> {
            bids.forEach(bid -> auction.handleBid(bid));
        });

        System.out.println("========================END OF BIDDING=======================");
        Map<String, BiddingItem> auctionBiddingItems = auction.getBiddingItems();
        assertEquals(NO_BIDDING_ITEMS, auctionBiddingItems.size(), "Bidding Items are not found");

        Thread.sleep(5000);
        AtomicInteger auctionBids = new AtomicInteger();
        auctionBiddingItems.forEach((s, biddingItem) -> {
            auctionBids.addAndGet(biddingItem.getProcessedBids().size());
        });
        assertEquals(NO_BIDS, auctionBids.get(), "Bids are not found");

        unprocessedBids.forEach((biddingItem, bids) -> {
            List<Bid> processedBids = auctionBiddingItems.get(biddingItem.getBiddingItemId()).getProcessedBids();
            for (int i=0; i<bids.size(); i++) {
                assertEquals(bids.get(i).getBidId(), processedBids.get(i).getBidId(), "Bids are not in executed in expected sequence");
            }
        });
    }
}