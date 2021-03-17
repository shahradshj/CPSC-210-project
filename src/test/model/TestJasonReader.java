package model;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class TestJasonReader {
    Exchange exchange;
    String source;
    JsonReader reader;

    @Test
    void testReadFileExist() {
        source = "./data/nasdaq.json";
        reader = new JsonReader(source);
        try {
            exchange = reader.read();
        } catch (IOException e) {
            fail("Shouldn't have happened!");
        }
        assertEquals(exchange.getName(),"Nasdaq");
        assertEquals(exchange.getMic(),"nasdaq");
    }

    @Test
    void testReadFileDoesNotExist() {
        source = "./data/random.json";
        reader = new JsonReader(source);
        try {
            exchange = reader.read();
            fail("Shouldn't have happened!");
        } catch (IOException e) {

        }
        assertEquals(exchange,null);
    }
}
