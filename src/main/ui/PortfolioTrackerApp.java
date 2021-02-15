package ui;

import model.Exchange;
import model.Stock;

import java.util.ArrayList;
import java.util.Scanner;

public class PortfolioTrackerApp {

    private Scanner input;
    private ArrayList<Exchange> exchanges;

    public PortfolioTrackerApp() {
        runTracker();
    }

    private void runTracker() {
        input = new Scanner(System.in);
        boolean keepGoing = true;
        String command = null;
        exchanges = new ArrayList<>();

        while (keepGoing) {
            displayMenu();
            command = this.input.next();

            if (command.equals("6")) {
                System.out.println("\nGoodbye!");
                keepGoing = false;
            } else {
                menuSwitchCases(command);
            }
        }
    }

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
        System.out.println("\t6 -> quit");
    }


    private void listOverview() {
        String summery = "";
        if (this.exchanges.isEmpty()) {
            System.out.println("You do not have any stocks!");
        } else {
            for (Exchange s : exchanges) {
                summery += s.listOfStocks() + "\n";
            }
            System.out.print(summery);
        }
    }

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
            Stock stock = exchange.searchForName(mic);
            if (stock == null) {
                System.out.println("Stock does not exist!");
                return null;
            } else {
                return stock;
            }
        }
    }

    private void updatePrice() {
        Stock stock = lookForStock();
        if (!(stock == null)) {
            System.out.println("Please enter new price:");
            double newPrice;
            newPrice = input.nextDouble();
            stock.updateMarketPrice(newPrice);
            listOverview();
        }
    }

    private void sell() {
        Stock stock = lookForStock();
        if (!(stock == null)) {
            System.out.println("Please enter number of shares that you have sold:");
            int quantity;
            quantity = input.nextInt();
            if (quantity > stock.getQuantity()) {
                System.out.println("Sorry! You do not have that many shares!");
            } else {
                System.out.println("Please enter your sell price:");
                double sellPrice;
                sellPrice = input.nextDouble();
                stock.sell(quantity, sellPrice);
                listOverview();
            }
        }
    }

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
        quantity = input.nextInt();
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

    private Exchange searchForNameOfExchange(String mic) {
        for (Exchange ex : this.exchanges) {
            if (mic.equals(ex.getMic())) {
                return ex;
            }
        }
        return null;
    }

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

}
