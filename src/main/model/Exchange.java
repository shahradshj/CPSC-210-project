package model;

// This class represent a stock exchange.

import java.util.LinkedList;

public class Exchange {
    private String name;
    private String mic; // market identifier code
    private String country;

    private LinkedList<Stock> stocks;

    public Exchange(String name, String mic, String country) {
        this.name = name;
        this.mic = mic;
        this.country = country;
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
