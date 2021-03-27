package ui;

import model.Exchange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Portfolio tracker application GUI
public class PortfolioTrackerAppGUI implements ActionListener {

    private static final int WIDTH = 1000;
    private static final int HEIGHT = 1000;
    private static final String JSON_STORE = "./data/";
    private ArrayList<Exchange> exchanges;
    private String summary;

    JButton load;
    JButton save;
    JButton addStock;
    JButton addExchange;

    JLabel displayString;
    JFrame frame;

    PortfolioTrackerAppGUI() {
        frame = new JFrame("Portfolio tracker");
        frame.setLayout(new FlowLayout());
        frame.setSize(WIDTH,HEIGHT);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        exchanges = new ArrayList<>();
        this.summary = listOverview();
        displayString = new JLabel(this.summary);
        addButtons();
        frame.add(displayString);

        JLabel label = new JLabel("test label");
        frame.add(label);

        frame.setVisible(true);
    }

    private void addButtons() {
        addExchange = new JButton("Add Exchange");
        addExchange.addActionListener(this);
        addExchange.setActionCommand("exchange");
        addStock = new JButton("stock");
        addStock.addActionListener(this);
        save = new JButton("save");
        save.addActionListener(this);
        load = new JButton("load");
        load.addActionListener(this);

        frame.add(addExchange);
        frame.add(addStock);
        frame.add(save);
        frame.add(load);
        frame.pack();
    }

    //    /**
//     * Invoked when an action occurs.
//     *
//     * @param
//     */
    @Override
    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals("exchange")) {
//
//        } else if (e.getActionCommand().equals("stock")) {
//
//        } else if (e.getActionCommand().equals("save")) {
//
//        } else {
//
//        }
    }

    // EFFECTS: prints the summary of all the stocks in all of the exchanges
    private String listOverview() {
        String summery = "";
        if (this.exchanges.isEmpty()) {
            return "You do not have any stocks!";
        } else {
            for (Exchange s : exchanges) {
                summery += s.listOfStocks() + "";
            }
            return summery;
        }
    }
}
