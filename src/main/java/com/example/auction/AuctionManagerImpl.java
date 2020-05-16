package com.example.auction;

import com.example.auction.data.DataSource;
import com.example.auction.events.AuctionEvent;
import com.example.auction.model.Auction;
import com.example.auction.services.AuctionService;
import com.example.auction.services.AuctionServiceImpl;
import com.example.auction.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import javax.inject.Inject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.example.auction.events.AuctionEvent.Action.START;
import static com.example.auction.events.AuctionEvent.Action.STOP;
import static java.time.temporal.ChronoUnit.SECONDS;

public class AuctionManagerImpl implements AuctionManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuctionManager.class);

    private final Map<String, AuctionService> auctions;
    private final ScheduledExecutorService auctionScheduler = Executors.newScheduledThreadPool(2);
    private final DataSource dataSource;

    @Inject
    public AuctionManagerImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        auctions = new ConcurrentHashMap<>();
        dataSource.getAuctions().values().forEach(this::createAuctionService);
        scheduleAuctions();
    }

    private void createAuctionService(Auction auction) {
        AuctionService service = new AuctionServiceImpl(auction, dataSource);
        auctions.put(auction.getAuctionId(), service);
    }

    @Override
    public void startAuction(String auctionId) {
        AuctionService service = auctions.get(auctionId);
        if (service != null) {
            Mono<AuctionEvent> start = Mono.just(new AuctionEvent(auctionId, START));
            auctionScheduler.schedule(() -> start.subscribe(service::handleEvent)
                    , TimeUtils.utcNow().until(service.getAuction().getStartTime(), SECONDS), TimeUnit.SECONDS);
        } else {
            LOGGER.error("Cannot find Auction {} to Start", auctionId);
        }
    }

    @Override
    public void stopAuction(String auctionId) {
        AuctionService service = auctions.get(auctionId);
        if (service != null) {
            Mono<AuctionEvent> stop = Mono.just(new AuctionEvent(auctionId, STOP));
            auctionScheduler.schedule(() -> stop.subscribe(service::handleEvent)
                    , TimeUtils.utcNow().until(service.getAuction().getEndTime(), SECONDS), TimeUnit.SECONDS);

        }
    }

    @Override
    public void scheduleAuctions() {

    }

    @Override
    public AuctionService getAuctionService(String auctionId) {
        return auctions.get(auctionId);
    }

}
