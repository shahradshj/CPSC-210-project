package model;

// This class represent a stock exchange.

import java.util.ArrayList;

public class Exchange {
    private String name;
    private String mic; // market identifier code
    private String country;

    private ArrayList<Stock> stocks;

    public Exchange(String name, String mic, String country) {
        this.name = name;
        this.mic = mic;
        this.country = country;
        stocks = new ArrayList<>();
    }

    public boolean addStock(Stock stock) {
        Boolean exist = this.stocks.contains(stock);
        if (!exist) {
            this.stocks.add(stock);
        }
        return !exist;
    }
    public Stock makeStock(String name, String symbol, int quantity, double buyPrice, double divYield) {
        Stock stock = new Stock(name, symbol, quantity, buyPrice, divYield);
        return stock;
    }

    public String listOfStocks() {
        String summery = this.name + " (" + this.mic + "):\n";
        if (this.stocks.isEmpty()) {
            summery += "\tThere is no stock in this exchange!\n";
            return summery;
        } else {
            for (Stock s : this.stocks) {
                summery += "\t" + s.overview() + "\n";
            }
            return summery;
        }
    }

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
