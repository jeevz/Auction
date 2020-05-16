package com.example.auction.items;

public class Item {

    private String itemId;
    private String itemName;
    private String shortDescription;
    private String description;
    private Long basePrice;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getBasePrice() {
        return basePrice;
    }

    public void setBasePrice(Long basePrice) {
        this.basePrice = basePrice;
    }
}
