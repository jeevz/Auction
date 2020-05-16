package com.example.auction.users;

public class User {

    public static enum Role {
        BIDDER,
        SELLER,
        AUCTIONEER,
        ADMIN;
    }
    private String userId;
    private String userName;
    private Role role;

    public User(String userId, String userName, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.role = role;
    }

    public String getUserId() {
        return userId;
    }
}
