package model;

// This class represent a stock exchange.

import java.util.ArrayList;
import java.util.LinkedList;

public class Exchange {
    private String name;
    private String mic; // market identifier code
    private String country;

    private ArrayList<Stock> stocks;

    public Exchange(String name, String mic, String country) {
        this.name = name;
        this.mic = mic;
        this.country = country;
    }

    public boolean addStock(String name, String symbol, int quantity, double buyPrice, double divYield) {
        Stock stock = new Stock(name, symbol, quantity, buyPrice, divYield);
        Boolean exist = this.stocks.contains(stock);
        if (!exist) {
            this.stocks.add(stock);
        }
        return !exist;
    }

    public String listOfStocks() {
        String summery = "";
        for (Stock s : stocks) {
            summery += s.overview() + "\n";
        }
        return summery;
    }

    public Stock searchForName(String name) {
        for (Stock s : this.stocks) {
            if (name.equals(s.getName())) {
                return s;
            }
        }
        return null;
    }

    public String getCountry() {
        return country;
    }

    public String getMic() {
        return mic;
    }

    public String getName() {
        return name;
    }
}
