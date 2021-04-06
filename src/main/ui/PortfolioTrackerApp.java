package ui;

import exception.DidNotFindStock;
import exception.NegativeNumber;
import exception.NotSufficientFund;
import model.Exchange;
import model.Stock;
import persistence.JsonReader;
import persistence.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

// Portfolio tracker application console
public class PortfolioTrackerApp {

    private static final String JSON_STORE = "./data/";
    private Scanner input;
    private ArrayList<Exchange> exchanges;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: runs the portfolio tracker application
    public PortfolioTrackerApp() {
        runTracker();
    }

    // MODIFIES: this
    // EFFECTS: processes user input
    private void runTracker() {
        input = new Scanner(System.in);
        boolean keepGoing = true;
        String command = null;
        exchanges = new ArrayList<>();

        while (keepGoing) {
            displayMenu();
            command = this.input.next();

            if (command.equals("q")) {
                System.out.println("\nGoodbye!");
                keepGoing = false;
            } else if (command.equals("6")) {
                saveList();
            } else if (command.equals("7")) {
                loadList();
            } else {
                menuSwitchCases(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: select the appropriate function based on the user input
    private void menuSwitchCases(String command) {
        switch (command) {
            case "1":
                newExchange();
                break;
            case "2":
                newStock();
                break;
            case "3":
                sell();
                break;
            case "4":
                updatePrice();
                break;
            case "5":
                listOverview();
                break;
            default:
                System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nWelcome to your Portfolio Tracker");
        System.out.println("\nSelect from:");
        System.out.println("\t1 -> add new exchange");
        System.out.println("\t2 -> add new stock");
        System.out.println("\t3 -> sell stocks");
        System.out.println("\t4 -> update market price");
        System.out.println("\t5 -> see list of all stocks");
        System.out.println("\t6 -> save an exchange");
        System.out.println("\t7 -> load an exchange");
        System.out.println("\tq -> quit");
    }

    // EFFECTS: prints the summary of all the stocks in all of the exchanges
    private void listOverview() {
        String summery = "";
        if (this.exchanges.isEmpty()) {
            System.out.println("You do not have any stocks!");
        } else {
            for (Exchange s : exchanges) {
                summery += s.listOfStocks() + "";
            }
            System.out.print(summery);
        }
    }

    // EFFECTS: find a stock from the name given by the user and returns it
    private Stock lookForStock() {
        String mic;
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange for your stock:");
        mic = input.next();
        Exchange exchange = searchForNameOfExchange(mic);
        if (exchange == null) {
            System.out.println("Exchange does not exist!");
            return null;
        } else {
            System.out.println("Please the name of the stock:");
            mic = input.next();
            try {
                Stock stock = exchange.searchForName(mic);
                return stock;
            } catch (DidNotFindStock didNotFindStock) {
                System.out.println("Stock does not exist!");
                return null;
            }
//            Stock stock = exchange.searchForName(mic);
//            if (stock == null) {
//                System.out.println("Stock does not exist!");
//                return null;
//            } else {
//                return stock;
//            }
        }
    }

    // MODIFIES: this
    // EFFECTS: update the market price of a stock given by the user
    private void updatePrice() {
        Stock stock = lookForStock();
        if (!(stock == null)) {
            System.out.println("Please enter new price:");
            double newPrice;
            newPrice = input.nextDouble();
            try {
                stock.updateMarketPrice(newPrice);
            } catch (NegativeNumber negativeNumber) {
                System.out.println("Error! Price should be positive!");
            }
            listOverview();
        }
    }

    // MODIFIES: this
    // EFFECTS: sell a number of shares for a given stock by the user
    private void sell() {
        Stock stock = lookForStock();
        if (!(stock == null)) {
            System.out.println("Please enter number of shares that you have sold:");
            int quantity;
//            quantity = input.nextInt();
            quantity = getIntForNumberOfStocks();
            if (quantity > stock.getQuantity()) {
                System.out.println("Sorry! You do not have that many shares!");
            } else {
                System.out.println("Please enter your sell price:");
                double sellPrice;
                sellPrice = input.nextDouble();
                try {
                    stock.sell(quantity, sellPrice);
                } catch (NegativeNumber negativeNumber) {
                    System.out.println("Error! Price should be positive!");
                } catch (NotSufficientFund notSufficientFund) {
                    System.out.println("Error! You do not have that many shares available!");
                } finally {
                    listOverview();
                }
            }
        }
    }

    // EFFECTS: returns an int for number of stocks or throws an NotAnIntegerException
    private int getIntForNumberOfStocks() {
        int num;
        try {
            num = input.nextInt();
        } catch (Exception e) {
            System.out.println("Please enter an integer:");
            if (input.hasNext()) {
                input.next();
            }
            num = getIntForNumberOfStocks();
        }
        return num;
    }

    // MODIFIES: this
    // EFFECTS: construct a new stock in an exchange, locate its exchange and ensures that it does not exist already
    private void newStock() {
        String mic;
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange for your stock:");
        mic = input.next();
        Exchange exchange = searchForNameOfExchange(mic);
        if (exchange == null) {
            System.out.println("Exchange does not exist!");
        } else {
            newStockElseCase(exchange);
        }
    }

    // MODIFIES: this
    // EFFECTS: construct a new stock in an exchange,
    private void newStockElseCase(Exchange exchange) {
        String name;
        String symbol;
        int quantity;
        double buyPrice;
        double divYield;

        System.out.println("Please enter the name of stock:");
        name = input.next();
        System.out.println("Please enter the symbol of:");
        symbol = input.next();
        System.out.println("Please enter the quantity of stock bought:");
        quantity = getIntForNumberOfStocks();
        System.out.println("Please enter the price you paid for each share:");
        buyPrice = input.nextDouble();
        System.out.println("Please enter the dividend yield in percentage for the stock");
        divYield = input.nextDouble();
        Stock stock = exchange.makeStock(name, symbol, quantity, buyPrice, divYield);
        if (!exchange.addStock(stock)) {
            System.out.println("Stock already exist!");
        } else {
            System.out.println("Your stock was added successfully!");
            listOverview();
        }
    }


    // EFFECTS: look for an exchange by it MIC and returns it, if it did nto exist, it returns null
    private Exchange searchForNameOfExchange(String mic) {
        for (Exchange ex : this.exchanges) {
            if (mic.equals(ex.getMic())) {
                return ex;
            }
        }
        return null;
    }

    // MODIFIES: this
    // EFFECTS: construct a new exchange in and add it to the list of exchanges
    private void newExchange() {
        String name;
        String mic;
        String country;

        System.out.println("Please enter the name of exchange:");
        name = input.next();
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange:");
        mic = input.next();
        System.out.println("Please enter the country of exchange:");
        country = input.next();

        Exchange exchange = new Exchange(name, mic, country);
        if (this.exchanges.contains(exchange)) {
            System.out.println("Exchange already exist!");
        } else {
            exchanges.add(exchange);
            System.out.println("You added a new exchange successful!");
            listOverview();
        }
    }

    // EFFECTS: saves an exchange to file
    private void saveList() {
        String mic;
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange for your stock:");
        mic = input.next();
        Exchange exchange = searchForNameOfExchange(mic);
        if (exchange == null) {
            System.out.println("Exchange does not exist!");
        } else {
            String location = JSON_STORE + mic + ".json";
            try {
                jsonWriter = new JsonWriter(location);
                jsonWriter.open();
                jsonWriter.write(exchange);
                jsonWriter.close();
                System.out.println("Saved " + mic + " to " + location);
            } catch (FileNotFoundException e) {
                System.out.println("Unable to write to file: " + location);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: loads an exchange from file
    private void loadList() {
        String mic;
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange for your stock:");
        mic = input.next();
        String location = JSON_STORE + mic + ".json";
        jsonReader = new JsonReader(location);
        try {
            Exchange exchange = jsonReader.read();
            exchanges.add(exchange);
            System.out.println("Loaded " + exchange.getName() + " from " + location);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + location);
        }
    }

}
