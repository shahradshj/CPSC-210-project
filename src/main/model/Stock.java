package model;

// Each object of this class represent a specific stock from the stock market

public class Stock {
    // Buy, sell, and market price are in $US.
    private int buyPrice;
    private int sellPrice;
    private int marketPrice;

    private int divYield; //dividend is in percentage;

    private int quantity;


    public Stock(int quantity, int buyPrice, int divYield) {
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = -1;
        this.marketPrice = buyPrice;
        this.divYield = divYield;
    }

    // Requires: positive integer
    // MODIFIES: this
    // EFFECTS: record the new market price and return profit in $US up to this poin
    public int updateMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
        return calcProfit(this.buyPrice, this.marketPrice, this.quantity);
    }

    // Requires: two positive integers, quantity is less that or equal number of owned stocks
    // MODIFIES: this
    // EFFECTS: sell number of given stock and return profit in $US
    public int sell(int quantity,int sellPrice) {
        this.sellPrice = sellPrice;
        this.marketPrice = sellPrice;
        this.quantity -= quantity;
        return calcProfit(this.buyPrice, this.sellPrice, quantity);
    }

    // EFFECTS: calculating the profit for a given buy and sell price for number of stocks
    private int calcProfit(int buy, int sell, int quantity) {
        return quantity * (sell - buy);
    }

    public int getDivYield() {
        return divYield;
    }

    public int getMarketPrice() {
        return marketPrice;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public int getBuyPrice() {
        return buyPrice;
    }

    public int getQuantity() {
        return quantity;
    }
}
