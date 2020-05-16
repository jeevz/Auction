package com.example.auction;

import com.example.auction.data.DataSource;
import com.example.auction.data.IgniteDataSource;
import com.example.auction.services.AuctionService;
import com.example.auction.services.AuctionServiceImpl;
import com.google.inject.AbstractModule;

public class DependencyModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(AuctionManager.class).to(AuctionManagerImpl.class).asEagerSingleton();
        bind(AuctionService.class).to(AuctionServiceImpl.class);
        bind(DataSource.class).to(IgniteDataSource.class).asEagerSingleton();
    }
}
