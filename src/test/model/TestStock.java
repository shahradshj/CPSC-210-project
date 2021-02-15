package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestStock {
    Stock aapl;
    Stock msft;
    Stock googl;

    @BeforeEach
    void setup() {
        aapl = new Stock("Apple", "AAPL", 5, 120, 2.5);
        msft = new Stock("Microsoft", "MSFT", 10, 220, 1.5);
        googl = new Stock("Alphabet", "GOOGL", 1, 2000, 0.5);
    }

    @Test
    void testOverview() {
        String aaplOverview = "Apple(AAPL)\n" +
                "\t - Total cost: $US120.0 * 5 = $US600.0\n" +
                "\t - Market Price: $US120.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(aaplOverview, aapl.overview());

        String msftOverview = "Microsoft(MSFT)\n" +
                "\t - Total cost: $US220.0 * 10 = $US2200.0\n" +
                "\t - Market Price: $US220.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(msftOverview, msft.overview());

        String googlOverview = "Alphabet(GOOGL)\n" +
                "\t - Total cost: $US2000.0 * 1 = $US2000.0\n" +
                "\t - Market Price: $US2000.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(googlOverview, googl.overview());
    }
}