package com.example.auction.services;

import com.example.auction.model.Bid;
import com.example.auction.users.User;
import com.example.auction.utils.TimeUtils;
import com.example.auction.events.AuctionEvent;
import com.example.auction.events.BiddingEvent;
import com.example.auction.bidding.BiddingItem;
import com.example.auction.data.DataSource;
import com.example.auction.model.Auction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class AuctionServiceImpl implements AuctionService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuctionService.class);

    ThreadPoolExecutor executor =
            (ThreadPoolExecutor) Executors.newCachedThreadPool();

    private final Auction auction;
    private final DataSource dataSource;
    private Map<String, BiddingItem> biddingItems;
    private List<User> registeredUsers;

    @Inject
    public AuctionServiceImpl(Auction auction, DataSource dataSource) {
        this.auction = auction;
        this.dataSource = dataSource;
        getBiddingItems();
        getRegisteredUsers();
    }

    @Override
    public Auction getAuction() {
        return auction;
    }

    @Override
    public void setBiddingItems() {
        dataSource.getBiddingItems(auction.getAuctionId()).ifPresent(items -> {
            biddingItems = items;
        });
    }

    @Override
    public void addBiddingItem(BiddingItem item) {
        biddingItems.put(item.getBiddingItemId(), item);
    }

    @Override
    public void scheduleItems() {
        biddingItems.values().forEach(biddingItem -> scheduleItem(biddingItem));
    }

    @Override
    public void scheduleItem(BiddingItem biddingItem) {
        Mono.just(new BiddingEvent(biddingItem.getBiddingItemId(), BiddingEvent.Action.START))
                .delaySubscription(TimeUtils.durationFromNow(biddingItem.getStartTime()))
                .subscribe(this::handleEvent);
        Mono.just(new BiddingEvent(biddingItem.getBiddingItemId(), BiddingEvent.Action.STOP))
                .delaySubscription(TimeUtils.durationFromNow(biddingItem.getEndTime()))
                .subscribe(this::handleEvent);
    }

    @Override
    public void setRegisteredUsers() {
        dataSource.getRegisteredUsers(auction.getAuctionId()).ifPresent(users -> {
            registeredUsers = users;
        });
    }

    @Override
    public void addRegisteredUser(User user) {
        registeredUsers.add(user);
    }

    @Override
    public void handleEvent(AuctionEvent event) {
        LOGGER.info("Handling Auction Event {} on Auction {}", event.getAction(), event.getAuctionId());
        event.onEvent(this);
    }

    @Override
    public void handleEvent(BiddingEvent event) {
        LOGGER.info("Handling Bidding Event {}", event);
        BiddingItem item = biddingItems.get(event.getBiddingItemId());
        if (item != null) {
            event.onEvent(item);
        } else {
            LOGGER.error("Cannot find Bidding Item {}", event.getBiddingItemId());
        }
    }

    @Override
    public void publish(Bid bid) {
        if (Bid.State.SUCCESS.equals(bid.getState())) {
            registeredUsers.forEach(user -> user.publish());
        }
    }

    @Override
    public void start() {
        auction.setState(Auction.State.OPENED);
        scheduleItems();
        LOGGER.info("Auction is Live");
    }

    @Override
    public void stop() {
        auction.setState(Auction.State.CLOSED);
        LOGGER.info("Auction is Closed");
    }

    @Override
    public void suspend() {
        auction.setState(Auction.State.SUSPENDED);
        LOGGER.info("Auction is Suspended");
    }

    @Override
    public void pause() {
        auction.setState(Auction.State.PAUSED);
        LOGGER.info("Auction is Paused");
    }

}
