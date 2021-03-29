package ui;

import model.Exchange;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Portfolio tracker application GUI
public class PortfolioTrackerAppGUI extends JFrame implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final String JSON_STORE = "./data/";
    private ArrayList<Exchange> exchanges;

    JButton load;
    JButton save;
    JButton addStock;
    JButton addExchange;

    JLabel displayLabel;
    JFrame frame;

    PortfolioTrackerAppGUI() {
        frame = new JFrame("Portfolio tracker");
        frame.setLayout(new FlowLayout());
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        exchanges = new ArrayList<>();
        addButtons();
        displayLabel = new JLabel("");
        listOverview();

//        frame.pack();
        frame.setVisible(true);
    }


    private void addButtons() {
        addExchange = new JButton("Add Exchange");
        addExchange.addActionListener(this);
        addExchange.setActionCommand("exchange");

        addStock = new JButton("Add Stock");
        addStock.addActionListener(this);
        addStock.setActionCommand("stock");

        save = new JButton("Save an exchange");
        save.addActionListener(this);
        save.setActionCommand("save");

        load = new JButton("Load an exchange");
        load.addActionListener(this);
        load.setActionCommand("load");

        frame.add(addExchange);
        frame.add(addStock);
        frame.add(save);
        frame.add(load);
    }

    //    /**
//     * Invoked when an action occurs.
//     *
//     * @param
//     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("exchange")) {
            newExchange();
        } else if (e.getActionCommand().equals("stock")) {
            newStock();
        } else if (e.getActionCommand().equals("save")) {

        } else if (e.getActionCommand().equals("load")) {

        }
    }

    // MODIFIES: this
    // EFFECTS: construct a new stock in an exchange, locate its exchange and ensures that it does not exist already
    private void newStock() {
        String mic;
        mic = JOptionPane.showInputDialog("Please enter the Market Identifier Code (MIC) of exchange for your stock:");

        Exchange exchange = searchForNameOfExchange(mic);
        if (exchange == null) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Exchange doesn't exist!");
        } else {
            newStockElseCase(exchange);
        }
    }

    // MODIFIES: this
    // EFFECTS: construct a new stock in an exchange,
    private void newStockElseCase(Exchange exchange) {
        Stock stock = makeTheStockForElseCase(exchange);

        if (!exchange.addStock(stock)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Stock already exist!");
        } else {
            JOptionPane.showMessageDialog(null,"Your stock was added successfully!");
            listOverview();
        }
    }

    private Stock makeTheStockForElseCase(Exchange exchange) {
        String name;
        String symbol;
        int quantity;
        double buyPrice;
        String buyString;
        String divString;
        double divYield;

        name = JOptionPane.showInputDialog("Please enter the name of stock:");

        symbol = JOptionPane.showInputDialog("Please enter the symbol of:");

        System.out.println("Please enter the quantity of stock bought:");
        quantity = getIntForNumberOfStocks();

        buyString = JOptionPane.showInputDialog("Please enter the price you paid for each share:");
        buyPrice = Double.parseDouble(buyString);

        System.out.println("Please enter the dividend yield in percentage for the stock");
        divString = JOptionPane.showInputDialog("Please enter the dividend yield in percentage for the stock");
        divYield = Double.parseDouble(divString);
        Stock stock = exchange.makeStock(name, symbol, quantity, buyPrice, divYield);
        return stock;
    }

    // EFFECTS: returns an int for number of stocks or throws an NotAnIntegerException
    private int getIntForNumberOfStocks() {
        int num;
        String intStr;
        try {
            intStr = JOptionPane.showInputDialog("Please enter the quantity of stock bought:");
            num = Integer.parseInt(intStr);
        } catch (Exception e) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Please enter an integer:");
            num = getIntForNumberOfStocks();
        }
        return num;
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

        name = JOptionPane.showInputDialog("Please enter the name of exchange:");
        mic = JOptionPane.showInputDialog("Please enter the Market Identifier Code (MIC) of exchange:");
        country = JOptionPane.showInputDialog("Please enter the country of exchange:");

        Exchange exchange = new Exchange(name, mic, country);
        if (alreadyInList(exchange)) {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null,"Exchange already exist!");
        } else {
            exchanges.add(exchange);
            JOptionPane.showMessageDialog(null,"Exchange added!");
            listOverview();
        }
    }

    protected boolean alreadyInList(Exchange ex) {
        return exchanges.contains(ex);
    }

    // EFFECTS: prints the summary of all the stocks in all of the exchanges
    private void listOverview() {
        String summery = "";
        if (this.exchanges.isEmpty()) {
            summery = "You do not have any stocks!";
        } else {
            for (Exchange s : exchanges) {
                summery += s.listOfStocks() + "";
            }
        }
        displayLabel.setText(summery);
        frame.add(displayLabel);
    }
}
