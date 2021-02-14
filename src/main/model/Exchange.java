package model;

// This class represent a stock exchange.

import java.util.LinkedList;

public class Exchange {
    private String name;
    private String mic; // market identifier code
    private String country;

    private LinkedList<Stock> stocks;

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
