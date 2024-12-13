package airlinemanagementsystem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame implements ActionListener {

    private JMenuBar menuBar;
    private JMenu detailsMenu, ticketsMenu;
    private JMenuItem addCustomerItem, flightDetailsItem, journeyDetailsItem, cancelItem, 
                      bookFlightItem, boardingPassItem, paymentItem;

    public Home() {
        // Frame settings
        setTitle("Airline Management System - Home");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // Background Image
        ImageIcon backgroundIcon = new ImageIcon(ClassLoader.getSystemResource("airlinemanagementsystem/icons/a2.png"));
        JLabel backgroundLabel = new JLabel(backgroundIcon);
        backgroundLabel.setBounds(0, 0, 1920, 1080); // Full-screen resolution
        add(backgroundLabel);

        // Menu bar
        menuBar = new JMenuBar();

        // Details Menu
        detailsMenu = new JMenu("Details");
        menuBar.add(detailsMenu);

        addCustomerItem = new JMenuItem("Add Customer");
        addCustomerItem.addActionListener(this);
        detailsMenu.add(addCustomerItem);

        flightDetailsItem = new JMenuItem("Flight Details");
        flightDetailsItem.addActionListener(this);
        detailsMenu.add(flightDetailsItem);

        journeyDetailsItem = new JMenuItem("Journey Details");
        journeyDetailsItem.addActionListener(this);
        detailsMenu.add(journeyDetailsItem);

        cancelItem = new JMenuItem("Cancel");
        cancelItem.addActionListener(this);
        detailsMenu.add(cancelItem);

        // Tickets Menu
        ticketsMenu = new JMenu("Tickets");
        menuBar.add(ticketsMenu);

        bookFlightItem = new JMenuItem("Book Flight");
        bookFlightItem.addActionListener(this);
        ticketsMenu.add(bookFlightItem);

        boardingPassItem = new JMenuItem("Boarding Pass");
        boardingPassItem.addActionListener(this);
        ticketsMenu.add(boardingPassItem);

        // Add Payment Menu Item under Tickets
        paymentItem = new JMenuItem("Make Payment");
        paymentItem.addActionListener(this);
        ticketsMenu.add(paymentItem);

        setJMenuBar(menuBar);

        // Make frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == addCustomerItem) {
            new AddCustomer();
        } else if (ae.getSource() == flightDetailsItem) {
            new FlightInfo();
        } else if (ae.getSource() == journeyDetailsItem) {
            new JourneyDetails();
        } else if (ae.getSource() == cancelItem) {
            new Cancel();
        } else if (ae.getSource() == bookFlightItem) {
            new BookFlight();
        } else if (ae.getSource() == boardingPassItem) {
            new BoardingPass();
        } else if (ae.getSource() == paymentItem) {
            new Payment(); // Open the Payment class1
        }
    }

    public static void main(String[] args) {
        new Home();
    }
}
