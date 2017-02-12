package com.silverbars;

/**
 * Created by gavriilgavriilidis
 */
public enum OrderType {

    BUY("BUY"),
    SELL("SELL");

    private String name;

    OrderType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}