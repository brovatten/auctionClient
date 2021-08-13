package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Bid {
    private String name;
    private int amount;


    public Bid(@JsonProperty("personId") String name,
               @JsonProperty("amount") int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getPerson() {
        return name;
    }

    public void setPerson(String personId) {
        this.name = personId;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
