package ui;

import model.Exchange;

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
                buyMore();
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
        System.out.println("\t4 -> buy more stocks");
        System.out.println("\t5 -> see list of all stocks");
        System.out.println("\t6 -> quit");
    }


    private void listOverview() {
        String summery = "";
        for (Exchange s : exchanges) {
            summery += s.listOfStocks() + "\n";
        }
        System.out.println(summery);

    }

    private void buyMore() {

    }

    private void sell() {
    }

    private void newStock() {
        String mic;
        System.out.println("Please enter the Market Identifier Code (MIC) of exchange for your stock:\n");
        mic = input.next();
        Exchange exchange = searchForNameOfExchange(mic);
        if (exchange == null) {
            System.out.println("Exchange does not exist!\n");
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

        System.out.println("Please enter the name of stock:\n");
        name = input.next();
        System.out.println("Please enter the symbol of:\n");
        symbol = input.next();
        System.out.println("Please enter the quantity of stock bought:\n");
        quantity = input.nextInt();
        System.out.println("Please enter the price you paid for each share:\n");
        buyPrice = input.nextDouble();
        System.out.println("Please enter the dividend yield for stock\n");
        divYield = input.nextDouble();
        if (!exchange.addStock(name, symbol, quantity, buyPrice, divYield)) {
            System.out.println("Stock already exist!\n");
        }
    }

    private Exchange searchForNameOfExchange(String mic) {
        for (Exchange ex : this.exchanges) {
            if (mic.equals(ex.getName())) {
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
            System.out.println("Exchange already exist!\n");
        } else {
            exchanges.add(exchange);
        }
    }

}
