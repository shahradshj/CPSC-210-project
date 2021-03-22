package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class TestJasonWriter {
    Exchange nasdaq;
    Exchange openedExchange;
    Stock aapl;
    Stock msft;
    String source;
    JsonWriter jsonWriter;
    JsonReader reader;
    String summaryOfOpenedExchange;

    @BeforeEach
    void setup() {
        nasdaq = new Exchange("Nasdaq Composite", "NASDAQ", "United States");
        aapl = new Stock("Apple", "AAPL", 5, 120, 2.5);
        msft = new Stock("Microsoft", "MSFT", 10, 220, 1.5);
        nasdaq.addStock(aapl);
        nasdaq.addStock(msft);
    }

    @Test
    void testOpenFileSuccessfully() {
        source = "./data/testWriter.json";
        jsonWriter = new JsonWriter(source);
        try {
            jsonWriter = new JsonWriter(source);
            jsonWriter.open();
            jsonWriter.write(nasdaq);
            jsonWriter.close();
            checkSavedFile(source);

        } catch (FileNotFoundException e) {
            fail("Shouldn't have happened!");
        }
    }

    private void checkSavedFile(String source) {
        reader = new JsonReader(source);
        try {
            openedExchange = reader.read();
        } catch (IOException e) {
            fail("Shouldn't have happened!");
        }
        assertEquals(openedExchange.getName(),"Nasdaq Composite");
        assertEquals(openedExchange.getMic(),"NASDAQ");
        summaryOfOpenedExchange = openedExchange.listOfStocks();
        assertEquals("Nasdaq Composite (NASDAQ):\n" +
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
                "\n",summaryOfOpenedExchange);

    }

    @Test
    void testOpenFileUnsuccessfully() {
        source = "./data/data/testWriter.json";
        jsonWriter = new JsonWriter(source);
        try {
            jsonWriter = new JsonWriter(source);
            jsonWriter.open();
            jsonWriter.write(nasdaq);
            jsonWriter.close();
            fail("Shouldn't have happened!");
        } catch (FileNotFoundException e) {

        }
    }
}
