package model;

// This class represent a stock exchange.

import java.util.ArrayList;

public class Exchange {
    private String name;
    private String mic; // market identifier code
    private String country;

    private ArrayList<Stock> stocks;

    // Construct a new exchange from the given information
    public Exchange(String name, String mic, String country) {
        this.name = name;
        this.mic = mic;
        this.country = country;
        stocks = new ArrayList<>();
    }

    // MODIFIES: this
    // EFFECTS: if stock does not already exist, it will return true and add the stock to the stocks list, else false
    public boolean addStock(Stock stock) {
        Boolean exist = this.stocks.contains(stock);
        if (!exist) {
            this.stocks.add(stock);
        }
        return !exist;
    }

    // EFFECTS: construct a new stock and returns it
    public Stock makeStock(String name, String symbol, int quantity, double buyPrice, double divYield) {
        Stock stock = new Stock(name, symbol, quantity, buyPrice, divYield);
        return stock;
    }

    // EFFECTS: returns a string that contains the summary of all the stocks in this exchange
    public String listOfStocks() {
        String summary = this.name + " (" + this.mic + "):\n";
        if (this.stocks.isEmpty()) {
            summary += "\tThere is no stock in this exchange!\n";
            return summary;
        } else {
            for (Stock s : this.stocks) {
                summary += "\t" + s.overview() + "\n";
            }
            return summary;
        }
    }


    // EFFECTS: returns the stock with the given name
    public Stock searchForName(String name) {
        for (Stock s : this.stocks) {
            if (name.equals(s.getName())) {
                return s;
            }
        }
        return null;
    }

//    public String getCountry() {
//        return country;
//    }

    public String getMic() {
        return mic;
    }

//    public String getName() {
//        return name;
//    }
}