package com.example.auction;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import java.util.concurrent.CountDownLatch;

public class AuctionEngine {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuctionEngine.class);

    private static final CountDownLatch shutdownHook = new CountDownLatch(1);

    @Inject
    private AuctionManager auctionManager;

    public void start() {
        try {
            auctionManager.scheduleAuctions();
            shutdownHook.wait();
        } catch (InterruptedException e) {
            LOGGER.error("Shutting Down Auction Engine");
        }
    }

    public void stop() {
        LOGGER.info("Stopping Auction Engine");
        shutdownHook.countDown();
    }

    public static void main(String... args) {
        Injector injector = Guice.createInjector(new DependencyModule());
        injector.getInstance(AuctionEngine.class).start();
    }
}
