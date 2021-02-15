package ui;

import model.Exchange;

import java.util.LinkedList;
import java.util.Scanner;

public class PortfolioTrackerApp {

    private Scanner input;
    private LinkedList<Exchange> exchanges;

    public PortfolioTrackerApp() {
        runTracker();
    }

    private void runTracker() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();

//            switch (command) {
//                case "1": newExchange();
//                break;
//                case "2": newStock();
//                break;
//            }
            if (command.equals("6")) {
                keepGoing = false;
            }
        }

        System.out.println("\nGoodbye!");
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

}
