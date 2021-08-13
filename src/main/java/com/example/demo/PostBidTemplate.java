package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

class PostBidTemplate {


    private final Integer bidId;
    private final Integer auctionId;
    private final Integer amount;

    PostBidTemplate(@JsonProperty("auctionId") Integer auctionId,
                    @JsonProperty("amount") Integer amount,
                    @JsonProperty("bidId") Integer bidId){
        this.auctionId = auctionId;
        this.amount = amount;
        this.bidId = bidId;
    }

    public Integer getBidId() {
        return bidId;
    }

    public Integer getAuctionId() {
        return auctionId;
    }

    public Integer getAmount() {
        return amount;
    }
}

