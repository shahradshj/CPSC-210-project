package persistence;

import model.Exchange;
import model.Stock;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

// Most of the code is from JsonSerializationDemo

// Represents a reader that reads exchange from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public Exchange read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseExchange(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses exchange from JSON object and returns it
    private Exchange parseExchange(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String mic = jsonObject.getString("mic");
        String country = jsonObject.getString("country");
        Exchange exchange = new Exchange(name, mic, country);
        addStocks(exchange, jsonObject);
        return exchange;
    }

    // MODIFIES: exchange
    // EFFECTS: parses thingies from JSON object and adds them to workroom
    private void addStocks(Exchange exchange, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("stocks");
        for (Object json : jsonArray) {
            JSONObject nextStock = (JSONObject) json;
            addStock(exchange, nextStock);
        }
    }

    // String name, String symbol, int quantity, double buyPrice, double divYield
    // MODIFIES: exchange
    // EFFECTS: parses thingy from JSON object and adds it to workroom
    private void addStock(Exchange exchange, JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        String symbol = jsonObject.getString("symbol");
        Integer quantity = jsonObject.getInt("quantity");
        double buyPrice = jsonObject.getDouble("buyPrice");
        double marketPrice = jsonObject.getDouble("marketPrice");
        double divYield = jsonObject.getDouble("divYield");
        Stock stock = new Stock(name, symbol, quantity, buyPrice, marketPrice, divYield);
        exchange.addStock(stock);
    }
}
