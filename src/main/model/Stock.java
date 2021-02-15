package model;

// Each object of this class represent a specific stock from the stock market

public class Stock {
    // Buy, sell, and market price are in $US.
    private String name;
    private String symbol;
    private int buyPrice;
    private int sellPrice;
    private int marketPrice;

    private int divYield; //dividend is in percentage;
    private int quantity;


    public Stock(String name, String symbol, int quantity, int buyPrice, int divYield) {
        this.name = name;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = -1; // -1 for sell price indicate that it has not been sold yet in any quantity
        this.marketPrice = buyPrice;
        this.divYield = divYield;
    }

    public String overview() {
        String title = this.name + "(" + this.symbol + ")\n";
        String cost = "\t - Total cost: $US" + this.buyPrice + " * " + this.quantity + " = $US" + totalCost() + "\n";
        String currentPrice = "\t - Market Price: $US" + this.marketPrice + "\n";
        String profit = "\t - Profit:  $US "  + calcProfit() + " - " + calcProfitPercent() + "%\n";

        return title + cost + currentPrice + profit;
    }

    // Requires: positive integer
    // MODIFIES: this
    // EFFECTS: record the new market price and return profit in $US up to this point
    public int updateMarketPrice(int marketPrice) {
        this.marketPrice = marketPrice;
        return calcProfit();
    }

    // Requires: two positive integers, quantity is less that or equal number of owned stocks
    // MODIFIES: this
    // EFFECTS: sell number of given stock and return profit made on the sold stocks in $US
    public int sell(int quantity,int sellPrice) {
        this.sellPrice = sellPrice;
        this.marketPrice = sellPrice;
        this.quantity -= quantity;
        return quantity * (this.buyPrice - sellPrice);
    }

    // REQUIRES: period is number of quarters of year
    // EFFECTS: calculating the profit gained from dividend for a given period of time
    public int divProfit(int period) {
        return this.quantity * this.marketPrice * this.divYield / 100;
    }

    // EFFECTS: calculating the profit in $US up to now
    private int calcProfit() {
        return this.quantity * (this.marketPrice - this.buyPrice);
    }

    // EFFECTS: calculating the profit in percentage up to now
    private int calcProfitPercent() {
        return calcProfit() / totalCost() * 100;
    }

    private int totalCost() {
        return this.buyPrice * this.quantity;
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

    public String getName() {
        return name;
    }

    public String getSymbol() {
        return symbol;
    }
}
