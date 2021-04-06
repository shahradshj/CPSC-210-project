package model;

import exception.DidNotFindStock;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import static org.junit.jupiter.api.Assertions.*;

public class TestExchange {
    Exchange nyse;
    Exchange nasdaq;
    Stock aapl;
    Stock msft;
    Stock googl;
    Stock amzn;
    Stock dis;

    Exchange readFromJSON;
    JsonReader jsonReader;
    JSONObject jsonObject;

    @BeforeEach
    void setup() {
        nyse = new Exchange("New York Stock Exchange", "NYSC", "US");
        nasdaq = new Exchange("Nasdaq Composite", "NASDAQ", "United States");
        aapl = new Stock("Apple", "AAPL", 5, 120, 2.5);
        msft = new Stock("Microsoft", "MSFT", 10, 220, 1.5);
        googl = new Stock("Alphabet", "GOOGL", 1, 2000, 0.5);
        amzn = new Stock("Amazon", "AMZN", 2, 3000, 1);
        dis = new Stock("Walt Disney Co", "DIS", 2, 190, 3);
    }

    @Test
    void toJason() {
        jsonObject = nasdaq.toJson();
    }

    @Test
    void testAddStockThatDoesNotExist() {
        assertTrue(nyse.addStock(amzn));
        try {
            assertEquals(amzn, nyse.searchForName("Amazon"));
        } catch (DidNotFindStock didNotFindStock) {
            fail("Shouldn't have happened");
        }
    }

    @Test
    void testAddStockThatDoesExist() {
        nyse.addStock(amzn);
        assertFalse(nyse.addStock(amzn));
    }

    @Test
    void testSearchForName() {
        nasdaq.addStock(aapl);
        nasdaq.addStock(msft);
        nasdaq.addStock(googl);
        nyse.addStock(dis);
        nyse.addStock(amzn);

        try {
            assertEquals(googl, nasdaq.searchForName("Alphabet"));
            assertEquals(dis, nyse.searchForName("Walt Disney Co"));
        } catch (DidNotFindStock didNotFindStock) {
            fail("Shouldn't have happened");
        }

    }

    @Test
    void testSearchForNameThrowsException() {
        nasdaq.addStock(aapl);
//        nasdaq.addStock(msft);
//        nasdaq.addStock(googl);
//        nyse.addStock(dis);
//        nyse.addStock(amzn);

        try {
            nasdaq.searchForName("google");
            fail("Shouldn't have happened");
        } catch (DidNotFindStock didNotFindStock) {
            // Expected
        }
    }

    @Test
    void testListOfStocks() {
        nasdaq.addStock(aapl);
        nasdaq.addStock(msft);
        nasdaq.addStock(googl);
        nyse.addStock(dis);
        nyse.addStock(amzn);
        String ny = "New York Stock Exchange (NYSC):\n" +
                "\tWalt Disney Co(DIS)\n" +
                "\t - Total cost: $US190.0 * 2 = $US380.0\n" +
                "\t - Market Price: $US190.0\n" +
                "\t - Today's value: $US380.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n" +
                "\n" +
                "\tAmazon(AMZN)\n" +
                "\t - Total cost: $US3000.0 * 2 = $US6000.0\n" +
                "\t - Market Price: $US3000.0\n" +
                "\t - Today's value: $US6000.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n" +
                "\n";

        String nas = "Nasdaq Composite (NASDAQ):\n" +
                "\tApple(AAPL)\n" +
                "\t - Total cost: $US120.0 * 5 = $US600.0\n" +
                "\t - Market Price: $US120.0\n" +
                "\t - Today's value: $US600.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n" +
                "\n" +
                "\tMicrosoft(MSFT)\n" +
                "\t - Total cost: $US220.0 * 10 = $US2200.0\n" +
                "\t - Market Price: $US220.0\n" +
                "\t - Today's value: $US2200.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n" +
                "\n" +
                "\tAlphabet(GOOGL)\n" +
                "\t - Total cost: $US2000.0 * 1 = $US2000.0\n" +
                "\t - Market Price: $US2000.0\n" +
                "\t - Today's value: $US2000.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n" +
                "\n";
        assertEquals(nas, nasdaq.listOfStocks());
        assertEquals(ny, nyse.listOfStocks());
    }

    @Test
    void testListOfExchangeForEmptyList() {
        String ny = "New York Stock Exchange (NYSC):\n" +
                "\tThere is no stock in this exchange!\n";
        assertEquals(ny, nyse.listOfStocks());
    }

    @Test
    void testMakeStock() {
        aapl = nasdaq.makeStock("Apple", "AAPL", 5, 120, 2.5);
        String aaplOverview = "Apple(AAPL)\n" +
                "\t - Total cost: $US120.0 * 5 = $US600.0\n" +
                "\t - Market Price: $US120.0\n" +
                "\t - Today's value: $US600.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(aaplOverview, aapl.overview());
    }
}
