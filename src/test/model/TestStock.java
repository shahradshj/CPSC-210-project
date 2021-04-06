package model;

import exception.NegativeNumber;
import exception.NotSufficientFund;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestStock {
    Stock aapl;
    Stock msft;
    Stock googl;
    Stock googlWithProfit;

    @BeforeEach
    void setup() {
        aapl = new Stock("Apple", "AAPL", 5, 120, 2.5);
        msft = new Stock("Microsoft", "MSFT", 10, 220, 1.5);
        googl = new Stock("Alphabet", "GOOGL", 1, 2000, 0.5);
        googlWithProfit = new Stock("Alphabetw/p", "GOOGLw/p", 1, 2000, 2500, 0.5);
    }

    @Test
    void testUpdateMarketPriceNoException() {
        try {
            aapl.updateMarketPrice(140);
            assertEquals(140, aapl.getMarketPrice());
            msft.updateMarketPrice(1000);
            assertEquals(1000, msft.getMarketPrice());
        } catch (NegativeNumber negativeNumber) {
            fail("Shouldn't have happened!");
        }
    }

    @Test
    void testUpdateMarketPriceThrowsExceptionNegativeNumber() {
        try {
            aapl.updateMarketPrice(-140);
        } catch (NegativeNumber negativeNumber) {
            //expected
        }
    }

    @Test
    void testUpdateMarketPriceNoExceptionFor0() {
        try {
            aapl.updateMarketPrice(0);
            assertEquals(0, aapl.getMarketPrice());
        } catch (NegativeNumber negativeNumber) {
            fail("Shouldn't have happened!");
        }
    }

    @Test
    void testSellNoException() {
        try {
            assertEquals(50, aapl.sell(5, 130));
            assertEquals(0, aapl.getQuantity());

            assertEquals(50, msft.sell(5, 230));
            assertEquals(5, msft.getQuantity());
        } catch (NegativeNumber negativeNumber) {
            fail("Shouldn't have happened! Negative Number!");
        } catch (NotSufficientFund notSufficientFund) {
            fail("Shouldn't have happened! Not sufficient fund!");
        }
    }

    @Test
    void testSellNotSufficientFundException() {
        try {
            aapl.sell(6, 130);

        } catch (NegativeNumber negativeNumber) {
            fail("Shouldn't have happened! Negative Number!");
        } catch (NotSufficientFund notSufficientFund) {
            // expected
        }
    }

    @Test
    void testSellNegativeQuantity() {
        try {
            assertEquals(50, aapl.sell(-5, 130));
        } catch (NegativeNumber negativeNumber) {
            // expected
        } catch (NotSufficientFund notSufficientFund) {
            fail("Shouldn't have happened! Not sufficient fund!");
        }
    }

    @Test
    void testSellNegativePrice() {
        try {
            assertEquals(50, aapl.sell(5, -130));
        } catch (NegativeNumber negativeNumber) {
            // expected
        } catch (NotSufficientFund notSufficientFund) {
            fail("Shouldn't have happened! Not sufficient fund!");
        }
    }

    @Test
    void testOverview() {
        String aaplOverview = "Apple(AAPL)\n" +
                "\t - Total cost: $US120.0 * 5 = $US600.0\n" +
                "\t - Market Price: $US120.0\n" +
                "\t - Today's value: $US600.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(aaplOverview, aapl.overview());

        String msftOverview = "Microsoft(MSFT)\n" +
                "\t - Total cost: $US220.0 * 10 = $US2200.0\n" +
                "\t - Market Price: $US220.0\n" +
                "\t - Today's value: $US2200.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(msftOverview, msft.overview());

        String googlOverview = "Alphabet(GOOGL)\n" +
                "\t - Total cost: $US2000.0 * 1 = $US2000.0\n" +
                "\t - Market Price: $US2000.0\n" +
                "\t - Today's value: $US2000.0\n" +
                "\t - Profit:  $US 0.0 - 0.0%\n";
        assertEquals(googlOverview, googl.overview());

        String googlWPOverview = "Alphabetw/p(GOOGLw/p)\n" +
                "\t - Total cost: $US2000.0 * 1 = $US2000.0\n" +
                "\t - Market Price: $US2500.0\n" +
                "\t - Today's value: $US2500.0\n" +
                "\t - Profit:  $US 500.0 - 25.0%\n";
        assertEquals(googlWPOverview, googlWithProfit.overview());
    }

    @Test
    void testDivProfitNoException() {
        try {
            assertEquals(10,googl.divProfit(4));
        } catch (NegativeNumber negativeNumber) {
            fail("Shouldn't have happened!");
        }
    }

    @Test
    void testDivProfitNegativePeriod() {
        try {
            googl.divProfit(-4);
        } catch (NegativeNumber negativeNumber) {
            // expected
        }
    }
}