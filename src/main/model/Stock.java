package model;

// Each object of this class represent a specific stock from the stock market

import exception.NegativeNumber;
import exception.NotSufficientFund;
import org.json.JSONObject;
import persistence.Writable;

public class Stock implements Writable {
    // Buy, sell, and market price are in $US.
    private String name;
    private String symbol;
    private double buyPrice;
    private double sellPrice;
    private double marketPrice;

    private double divYield; //dividend is in percentage;
    private int quantity;

    // Construct a new stock from the given information
    public Stock(String name, String symbol, int quantity, double buyPrice, double divYield) {
        this.name = name;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = -1; // -1 for sell price indicate that it has not been sold yet in any quantity
        this.marketPrice = buyPrice;
        this.divYield = divYield;
    }

    // Construct a new stock from the given information
    public Stock(String name, String symbol, int quantity, double buyPrice, double marketPrice, double divYield) {
        this.name = name;
        this.symbol = symbol;
        this.quantity = quantity;
        this.buyPrice = buyPrice;
        this.sellPrice = -1; // -1 for sell price indicate that it has not been sold yet in any quantity
        this.marketPrice = marketPrice;
        this.divYield = divYield;
    }

    // EFFECTS: returns a string that contains information about this stock
    public String overview() {
        String title = this.name + "(" + this.symbol + ")\n";
        String cost = "\t - Total cost: $US" + this.buyPrice + " * " + this.quantity + " = $US" + totalCost() + "\n";
        String currentPrice = "\t - Market Price: $US" + this.marketPrice + "\n";
        String cuttentValue = "\t - Today's value: $US" + this.marketPrice * this.quantity + "\n";
        String profit = "\t - Profit:  $US "  + calcProfit() + " - " + calcProfitPercent() + "%\n";

        return title + cost + currentPrice + cuttentValue + profit;
    }

    // MODIFIES: this
    // EFFECTS: record the new market price and return profit in $US up to this point
    public double updateMarketPrice(double marketPrice) throws NegativeNumber {
        if (marketPrice < 0) {
            throw new NegativeNumber();
        }
        this.marketPrice = marketPrice;
        return calcProfit();
    }

    // Requires: one positive integer and one positive double, quantity is less that or equal number of owned stocks
    // MODIFIES: this
    // EFFECTS: sell number of given stock and return profit made on the sold stocks in $US
    //          if quantity or sellPrice is negative throws NegativeNumber exception
    //          if doesn't have enough shares throws NotSufficientFund exception
    public double sell(int quantity, double sellPrice) throws NegativeNumber, NotSufficientFund {
        if (quantity < 0 || sellPrice < 0) {
            throw new NegativeNumber();
        }
        if (quantity > this.quantity) {
            throw new NotSufficientFund();
        }
        this.sellPrice = sellPrice;
        this.marketPrice = sellPrice;
        this.quantity -= quantity;
        return quantity * (sellPrice - this.buyPrice);
    }

//    // Requires: one positive integer and one positive double
//    // MODIFIES: this
//    // EFFECTS: add to the number of owned quantity and calculate new average for buy price, return new buy price
//    public double buyMore(int quantity, double buyPrice) {
//        this.buyPrice = (this.buyPrice * this.quantity + buyPrice * quantity) / (quantity + this.quantity);
//        this.marketPrice = buyPrice;
//        this.quantity += quantity;
//        return this.buyPrice;
//    }


    // EFFECTS: calculating the profit gained from dividend for a given period of time
    //          or throw NegativeNumber Exception if period is negative
    public double divProfit(int period) throws NegativeNumber {
        if (period < 0) {
            throw new NegativeNumber();
        }
        return this.quantity * this.marketPrice * this.divYield * period / 400;
    }

    // EFFECTS: calculating the profit in $US up to now
    private double calcProfit() {
        return this.quantity * (this.marketPrice - this.buyPrice);
    }

    // EFFECTS: calculating the profit in percentage up to now
    private double calcProfitPercent() {
        return calcProfit() / totalCost() * 100;
    }


    // EFFECTS: return the total cost of buying this stock
    private double totalCost() {
        return this.buyPrice * this.quantity;
    }

//    public double getDivYield() {
//        return divYield;
//    }
//
    public double getMarketPrice() {
        return marketPrice;
    }
//
//    public double getSellPrice() {
//        return sellPrice;
//    }
//
//    public double getBuyPrice() {
//        return buyPrice;
//    }

    public int getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("symbol", symbol);
        json.put("quantity", quantity);
        json.put("buyPrice", buyPrice);
        json.put("marketPrice", marketPrice);
        json.put("divYield", divYield);
        return json;
    }

//    public String getSymbol() {
//        return symbol;
//    }
}
